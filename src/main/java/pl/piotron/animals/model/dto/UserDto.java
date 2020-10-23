package pl.piotron.animals.model.dto;

import lombok.Data;
import pl.piotron.animals.model.UserRole;

import java.util.HashSet;
import java.util.Set;
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Set<UserRole> roles = new HashSet<>();

}
