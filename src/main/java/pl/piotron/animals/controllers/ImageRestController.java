package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(value = "/advertisement/{id}")
    public List<String> getAllByAdvertisementId (@PathVariable Long id)
    {
        return imagesService.getAllByAdvertisementId(id);
    }
    @GetMapping(value = "/advertisement/{id}/mainImage")
    public ResponseEntity<ImageStorage> getMainImage(@PathVariable Long id)
    {
        ImageStorage mainImage = imagesService.getMainImage(id);
        return ResponseEntity.ok(mainImage);
    }
}
