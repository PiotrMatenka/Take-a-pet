package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piotron.animals.exceptions.UserNotFoundException;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.services.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDto> getAll ()
    {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id)
    {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(userService.findByEmail(email));
    }
    @GetMapping("/{id}/advertisements")
    public List<ImageAdvertisementDto> getUserAdvertisements (@PathVariable Long id)
    {
        return userService.getUserAdvertisements(id);
    }

    @PostMapping("")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto user, BindingResult result)
    {
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zapisywany obiekt nie może mieć ustawionego Id");
        if (result.hasErrors())
        {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(err -> System.out.println(err.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UserDto savedUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser (@PathVariable Long id, @RequestBody UserDto user)
    {
        if (!id.equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        UserDto updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
}
