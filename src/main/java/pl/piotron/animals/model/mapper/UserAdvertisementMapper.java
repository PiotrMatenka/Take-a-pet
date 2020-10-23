package pl.piotron.animals.model.mapper;

import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.UserAdvertisementDto;

public class UserAdvertisementMapper {
    public static UserAdvertisementDto toDto (Advertisement advertisement)
    {
        UserAdvertisementDto dto = new UserAdvertisementDto();
        dto.setAdvertisementId(advertisement.getId());
        dto.setDescription(advertisement.getDescription());
        dto.setCity(advertisement.getCity());
        dto.setPrice(advertisement.getPrice());
        dto.setTitle(advertisement.getTitle());
        dto.setStart(advertisement.getStart());
        User user = advertisement.getUser();
        dto.setUserId(user.getId());
        dto.setUserFirstName(user.getFirstName());
        dto.setUserPhoneNumber(user.getPhoneNumber());
        dto.setUserEmail(user.getEmail());
        return dto;
    }
}
