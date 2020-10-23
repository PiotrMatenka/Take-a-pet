package pl.piotron.animals.model.mapper;

import org.springframework.stereotype.Service;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.Category;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.AdvertisementDto;
import pl.piotron.animals.repositories.CategoryRepository;
import pl.piotron.animals.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdvertisementMapper {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public AdvertisementMapper(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public AdvertisementDto toDto (Advertisement advertisement)
    {
        AdvertisementDto dto = new AdvertisementDto();
        dto.setId(advertisement.getId());
        dto.setCategory(advertisement.getCategory().getName());
        dto.setTitle(advertisement.getTitle());
        dto.setDescription(advertisement.getDescription());
        dto.setPrice(advertisement.getPrice());
        dto.setCity(advertisement.getCity());
        dto.setStart(advertisement.getStart());
        dto.setEnd(advertisement.getEnd());
        User user = advertisement.getUser();
        dto.setUserId(user.getId());

        return dto;
    }

    public Advertisement toEntity (AdvertisementDto dto)
    {
        Advertisement entity = new Advertisement();
        entity.setId(dto.getId());
        Optional<Category> category = categoryRepository.findByName(dto.getCategory());
        category.ifPresent(entity::setCategory);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCity(dto.getCity());
        entity.setStart(LocalDateTime.now());
        entity.setEnd(null);
        Optional<User> user = userRepository.findById(dto.getUserId());
        user.ifPresent(entity::setUser);
        return entity;
    }
}
