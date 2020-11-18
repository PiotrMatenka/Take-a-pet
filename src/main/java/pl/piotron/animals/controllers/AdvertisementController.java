package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piotron.animals.exceptions.AdvertisementNotFoundException;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.model.dto.AdvertisementDto;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.UserAdvertisementDto;
import pl.piotron.animals.services.AdvertisementService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }
    @GetMapping("")
    public List<ImageAdvertisementDto> getAll(@RequestParam(required = false) String title)
    {
        if (title != null)
            return advertisementService.getAllByTitle(title);
        else
            return advertisementService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> getById (@PathVariable Long id)
    {
        return advertisementService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(AdvertisementNotFoundException::new);
    }

    @GetMapping("/{id}/images")
    public List<String> getImagesById (@PathVariable Long id)
    {
        return advertisementService.getImagesById(id);
    }

    @GetMapping("/findByCategory/{category}")
    public List<ImageAdvertisementDto> getAllByCategory (@PathVariable String category)
    {
        return advertisementService.getAllByCategory(category);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<UserAdvertisementDto> getViewById (@PathVariable Long id)
    {
        return advertisementService.getViewById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(AdvertisementNotFoundException::new);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("")
    public ResponseEntity<AdvertisementDto> createAdvertisement(@Valid @RequestBody AdvertisementDto advertisement)
    {
        AdvertisementDto savedAdvertisement;
        try {
            savedAdvertisement = advertisementService.createAdvertisement(advertisement);
        }catch (AppException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAdvertisement.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAdvertisement);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/end")
    public ResponseEntity finishAdvertisement(@PathVariable Long id)
    {
        LocalDateTime endTime = advertisementService.finishAdvertisement(id);
        return ResponseEntity.accepted().body(endTime);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity updateAdvertisement (@PathVariable Long id, @RequestBody AdvertisementDto dto)
    {
        if (!id.equals(dto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        AdvertisementDto updatedAdver = advertisementService.updateAdvertisement(dto);
        return ResponseEntity.ok(updatedAdver);
    }
}
