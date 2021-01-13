package com.sleepseek.stay.DTO;

import com.sleepseek.image.DTO.ImageDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StayDTO {
    private Long id;

    private String name;

    private AddressDTO address;

    private String username;

    private String description;

    private String minPrice;

    private String phoneNumber;

    private String email;

    private String mainPhoto;

    private String category;

    private String createdAt;

    private List<String> photos;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddressDTO {
        private String city;
        private String street;
        private String zipCode;
        private String country;
        private Double longitude;
        private Double latitude;
    }
}


