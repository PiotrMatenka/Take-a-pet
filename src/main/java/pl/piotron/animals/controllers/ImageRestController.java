package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.piotron.animals.exceptions.ImageNotFoundException;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.services.ImagesService;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {
    private ImagesService imagesService;
    @Autowired
    public ImageRestController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ImageStorage> getAll()
    {
        return imagesService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageStorage> getImage(@PathVariable Long id)
    {
        return imagesService.getImage(id)
                .map(ResponseEntity::ok)
                .orElseThrow(ImageNotFoundException::new);
    }

    @GetMapping(value = "/advertisement/{id}")
    public List<ImageStorage> getAllByAdvertisementId (@PathVariable Long id)
    {
        return imagesService.getAllByAdvertisementId(id);
    }
    @GetMapping(value = "/advertisement/{id}/mainImage")
    public ResponseEntity<ImageStorage> getMainImage(@PathVariable Long id)
    {
        ImageStorage mainImage = imagesService.getMainImage(id);
        return ResponseEntity.ok(mainImage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ImageStorage> removeSingleImage (@PathVariable Long id)
    {
        imagesService.removeSingleImage(id);
        return ResponseEntity.noContent().build();
    }

}
