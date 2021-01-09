package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.piotron.animals.exceptions.DuplicateImageException;
import pl.piotron.animals.exceptions.FileExtensionException;
import pl.piotron.animals.services.ImagesService;


@Controller
public class ImageUploadController {
    private final ImagesService imagesService;

    @Autowired
    public ImageUploadController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PreAuthorize("isUser(#userId) or hasAuthority('ADMIN')")
    @PostMapping("/upload/{userId}/{advertisementId}")
    public String uploadImage  (@RequestParam("file")MultipartFile file, @PathVariable Long advertisementId, @PathVariable Long userId, Model model)
    {
        try{
            imagesService.storeImage(file, advertisementId);
            model.addAttribute("message", "Dodano zdjęcie, możesz dodać kolejne");
            return "imagesAdd";
        }catch (FileExtensionException e)
        {
            model.addAttribute("errorMessage", "Błędne rozszerzenie lub brak wybranego pliku");
            return "imagesAdd";
        }
        catch (DuplicateImageException e)
        {
            model.addAttribute("errorMessage", "Zdjęcie juz istnieje");
            return "imagesAdd";
        }
    }

    @GetMapping("/ads/edit/{advertisementId}")
    public String backToAdvertisement(@PathVariable Long advertisementId)
    {
        return "redirect:/#!/ads/edit/{advertisementId}";
    }

    @PreAuthorize("isUser(#userId) or hasAuthority('ADMIN')")
    @GetMapping("/upload/{userId}/{advertisementId}")
    public String homepage(@PathVariable Long advertisementId, @PathVariable Long userId) {
        return "imagesAdd";
    }

    @GetMapping("/errorTemplate")
    public String errorPage ()
    {
        return "errorTemplate";
    }


}
