package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piotron.animals.exceptions.UserNotFoundException;
import pl.piotron.animals.model.dto.EndedAdvertisementDto;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.UserAdvertisementDto;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.services.UserConfirmService;
import pl.piotron.animals.services.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserConfirmService userConfirmService;
    @Autowired
    public UserController(UserService userService, UserConfirmService userConfirmService) {
        this.userService = userService;
        this.userConfirmService = userConfirmService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public List<UserDto> getAll ()
    {
        return userService.findAll();
    }

    @PreAuthorize("isUser(#id) or hasAuthority('ADMIN')")
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

    @PreAuthorize("isUser(#id) or hasAuthority('ADMIN')")
    @GetMapping("/{id}/advertisements")
    public List<ImageAdvertisementDto> getUserAdvertisements (@PathVariable Long id)
    {
        return userService.getUserAdvertisements(id);
    }

    @PreAuthorize("isUser(#id) or hasAuthority('ADMIN')")
    @GetMapping("/{id}/advertisements/ended")
    public List<EndedAdvertisementDto> getEndedAdvertisements (@PathVariable Long id)
    {
        return userService.getEndedAdvertisements(id);
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
        userConfirmService.sendConfirmationEmail(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @PreAuthorize("isUser(#id) or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser (@PathVariable Long id, @RequestBody UserDto user)
    {
        if (!id.equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        UserDto updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
}
