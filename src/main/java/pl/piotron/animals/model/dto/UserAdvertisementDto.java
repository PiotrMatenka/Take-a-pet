package pl.piotron.animals.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAdvertisementDto {
    private Long advertisementId;
    private String title;
    private double price;
    private String description;
    private String city;
    private LocalDateTime start;
    private Long userId;
    private String userFirstName;
    private String userPhoneNumber;
    private String userEmail;

}
