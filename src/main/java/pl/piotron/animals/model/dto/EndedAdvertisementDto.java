package pl.piotron.animals.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EndedAdvertisementDto {
    private Long advertisementId;
    private String title;
    private double price;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long userId;
}
