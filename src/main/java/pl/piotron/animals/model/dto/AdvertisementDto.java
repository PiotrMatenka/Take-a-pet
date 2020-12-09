package pl.piotron.animals.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AdvertisementDto {
    private Long id;
    private String category;
    private String title;
    private String description;
    private double price;
    private String city;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long userId;
    private boolean isAcceptedByUser;
    private boolean isAcceptedByAdmin;
}
