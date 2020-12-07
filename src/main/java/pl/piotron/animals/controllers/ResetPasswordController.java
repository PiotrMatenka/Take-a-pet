package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.services.UserConfirmService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/userPassword")
public class ResetPasswordController {
    private UserConfirmService confirmService;
    @Autowired
    public ResetPasswordController(UserConfirmService confirmService) {
        this.confirmService = confirmService;
    }

    @PostMapping ("/resetPassword")
    public ResponseEntity<?> resetPassword (@RequestParam("email") String email)
    {
        confirmService.sendPasswordResetEmail(email);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isUser(#id) or hasAuthority('ADMIN')")
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<UserDto> changePassword(@Valid @PathVariable Long id, @RequestParam String password)
    {
        UserDto userDto = confirmService.setNewPassword(id, password);
        return ResponseEntity.ok(userDto);
    }


}
