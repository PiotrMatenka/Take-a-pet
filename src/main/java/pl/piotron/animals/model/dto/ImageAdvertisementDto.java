package pl.piotron.animals.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageAdvertisementDto {
    private Long id;
    private String title;
    private LocalDateTime start;
    private double price;
    private Long imageId;
    private String uploadUrl;
    private String city;

}
