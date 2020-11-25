package pl.piotron.animals.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Pattern(regexp = "[\\d]{9}")
    @Size(min = 9, max = 9)
    private String phoneNumber;
    @OneToOne(mappedBy = "userDetails")
    private User user;
}
