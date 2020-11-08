package pl.piotron.animals.model.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.UserRole;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.repositories.UserRoleRepository;

@Service
public class UserMapper {
    private final UserRoleRepository userRoleRepository;
    private PasswordEncoder encoder;
    private static final String DEFAULT_ROLE=  "USER";


    public UserMapper( UserRoleRepository userRoleRepository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRoleRepository = userRoleRepository;
    }

    public UserDto userDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        String password = encoder.encode(user.getPassword());
        dto.setPassword(password);
        dto.setRoles(user.getRoles());
        return dto;
    }
    public User toEntity(UserDto user) {
        User entity = new User();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        String password = encoder.encode(user.getPassword());
        entity.setPassword(password);
        entity.setPhoneNumber(user.getPhoneNumber());
        UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        entity.setRoles(user.getRoles());
        return entity;
    }
}
