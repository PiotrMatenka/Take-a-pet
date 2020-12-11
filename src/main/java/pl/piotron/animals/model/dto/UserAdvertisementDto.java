package pl.piotron.animals.model.dto;

import lombok.Data;

@Data
public class UserAdvertisementDto {
    private Long id;
    private String title;
    private Long imageId;
    private String uploadUrl;
    private Long userId;
    private String email;
}
