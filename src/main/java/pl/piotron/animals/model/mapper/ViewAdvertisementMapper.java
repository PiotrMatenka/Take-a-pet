package pl.piotron.animals.model.mapper;

import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.UserDetails;
import pl.piotron.animals.model.dto.ViewAdvertisementDto;
public class ViewAdvertisementMapper {

    public static ViewAdvertisementDto toDto (Advertisement advertisement)
    {
        ViewAdvertisementDto dto = new ViewAdvertisementDto();
        dto.setAdvertisementId(advertisement.getId());
        dto.setDescription(advertisement.getDescription());
        dto.setCity(advertisement.getCity());
        dto.setPrice(advertisement.getPrice());
        dto.setTitle(advertisement.getTitle());
        dto.setStart(advertisement.getStart());
        dto.setAcceptedByUser(advertisement.isAcceptedByUser());
        User user = advertisement.getUser();
        dto.setUserId(user.getId());
        UserDetails userDetails = user.getUserDetails();
        dto.setUserFirstName(userDetails.getFirstName());
        dto.setUserPhoneNumber(userDetails.getPhoneNumber());
        dto.setUserEmail(user.getEmail());
        return dto;
    }
}
