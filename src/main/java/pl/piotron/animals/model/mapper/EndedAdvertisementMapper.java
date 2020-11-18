package pl.piotron.animals.model.mapper;

import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.EndedAdvertisementDto;

public class EndedAdvertisementMapper {
    public static EndedAdvertisementDto toDto (Advertisement advertisement)
    {
        EndedAdvertisementDto dto = new EndedAdvertisementDto();
        dto.setAdvertisementId(advertisement.getId());
        dto.setTitle(advertisement.getTitle());
        dto.setStart(advertisement.getStart());
        dto.setEnd(advertisement.getEnd());
        dto.setPrice(advertisement.getPrice());
        User user = advertisement.getUser();
        dto.setUserId(user.getId());
        return dto;
    }
}
