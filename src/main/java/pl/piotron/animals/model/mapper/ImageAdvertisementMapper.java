package pl.piotron.animals.model.mapper;

import org.springframework.stereotype.Service;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.services.ImagesService;

@Service
public class ImageAdvertisementMapper {
   private final ImagesService imagesService;
   public ImageAdvertisementMapper(ImagesService imagesService)
   {
       this.imagesService = imagesService;
   }

    public ImageAdvertisementDto toDto (Advertisement advertisement)
    {
        ImageAdvertisementDto dto = new ImageAdvertisementDto();
        dto.setId(advertisement.getId());
        dto.setTitle(advertisement.getTitle());
        dto.setPrice(advertisement.getPrice());
        dto.setStart(advertisement.getStart());
        dto.setCity(advertisement.getCity());
        try{
            ImageStorage image = imagesService.getMainImage(advertisement.getId());
            dto.setImageId(image.getId());
            dto.setUploadUrl(image.getUploadUrl());
        }catch (NullPointerException e)
        {
            e.getMessage();
        }
        dto.setAcceptedByAdmin(advertisement.isAcceptedByAdmin());
        return dto;
    }
}
