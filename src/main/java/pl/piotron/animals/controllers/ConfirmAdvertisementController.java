package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.piotron.animals.model.EmailResponse;
import pl.piotron.animals.model.dto.UserAdvertisementDto;
import pl.piotron.animals.services.ConfirmAdvertisementService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/checkingAdvertisements")
public class ConfirmAdvertisementController {
    private final ConfirmAdvertisementService confirmAdvertisementService;
    @Autowired
    public ConfirmAdvertisementController(ConfirmAdvertisementService confirmAdvertisementService) {
        this.confirmAdvertisementService = confirmAdvertisementService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/acceptedByUser")
    public List<UserAdvertisementDto> getAllAcceptedByUser()
    {
        return confirmAdvertisementService.getAllAcceptedByUser();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/userAccept")
    public ResponseEntity acceptedByUser(@PathVariable Long id)
    {
        return ResponseEntity.accepted().body(confirmAdvertisementService.acceptAdverByUser(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/adminAccept")
    public ResponseEntity<?> acceptedByAdmin(@PathVariable Long id)
    {
        return ResponseEntity.accepted().body(confirmAdvertisementService.acceptAdverByAdmin(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/end")
    public ResponseEntity finishAdvertisement(@PathVariable Long id)
    {
        LocalDateTime endTime = confirmAdvertisementService.finishAdvertisement(id);
        return ResponseEntity.accepted().body(endTime);
    }
    @PostMapping(value = "/{id}/toCorrection")
    public ResponseEntity<EmailResponse> sendCorrectionEmail(@PathVariable Long id, @RequestBody String response)
    {
        EmailResponse emailResponse = confirmAdvertisementService.sendToCorrectionEmail(id, response);
        return ResponseEntity.ok(emailResponse);
    }
}
