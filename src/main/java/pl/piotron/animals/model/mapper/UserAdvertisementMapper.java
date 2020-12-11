package pl.piotron.animals.model.mapper;

import org.springframework.stereotype.Service;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.UserAdvertisementDto;
import pl.piotron.animals.services.ImagesService;

@Service
public class UserAdvertisementMapper {
    private final ImagesService imagesService;

    public UserAdvertisementMapper(ImagesService imagesService)
    {
        this.imagesService = imagesService;
    }

    public UserAdvertisementDto toDto(Advertisement advertisement)
    {
        UserAdvertisementDto dto = new UserAdvertisementDto();
        dto.setId(advertisement.getId());
        dto.setTitle(advertisement.getTitle());
        try{
            ImageStorage image = imagesService.getMainImage(advertisement.getId());
            dto.setImageId(image.getId());
            dto.setUploadUrl(image.getUploadUrl());
        }catch (NullPointerException e)
        {
            e.getMessage();
        }
        User user = advertisement.getUser();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
