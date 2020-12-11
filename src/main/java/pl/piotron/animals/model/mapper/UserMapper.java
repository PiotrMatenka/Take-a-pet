package pl.piotron.animals.model.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.UserDetails;
import pl.piotron.animals.model.UserRole;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.repositories.UserRoleRepository;

@Service
public class UserMapper {
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder encoder;
    private static final String DEFAULT_ROLE=  "USER";

    public UserMapper( UserRoleRepository userRoleRepository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRoleRepository = userRoleRepository;
    }

    public UserDto userDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        UserDetails userDetails = user.getUserDetails();
        dto.setFirstName(userDetails.getFirstName());
        dto.setLastName(userDetails.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(userDetails.getPhoneNumber());
        String password = encoder.encode(user.getPassword());
        dto.setPassword(password);
        dto.setEnabled(user.isEnabled());
        dto.setRoles(user.getRoles());
        return dto;
    }
    public User toEntity(UserDto user) {
        User entity = new User();
        entity.setId(user.getId());
        UserDetails userDetails = detailsMapper(user);
        entity.setUserDetails(userDetails);
        entity.setEmail(user.getEmail());
        try {
            String password = encoder.encode(user.getPassword());
            entity.setPassword(password);
        }catch (IllegalArgumentException e)
        {
            e.getMessage();
        }
        UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        entity.setRoles(user.getRoles());
        entity.setEnabled(false);
        return entity;
    }

    public UserDetails updateUser(UserDto user) {
        return detailsMapper(user);
    }
    public UserDto updatedToDto (UserDetails user)
    {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    private UserDetails detailsMapper(UserDto user)
    {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(user.getId());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setPhoneNumber(user.getPhoneNumber());
        return userDetails;
    }
    
}
