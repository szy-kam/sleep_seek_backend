package com.sleepseek.stay.DTO;

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

    private Long userId;

    private String description;

    private String price;

    private String contactInfo;

    private Long mainPhoto;

    private String createdAt;

    private List<Long> images;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddressDTO {
        private String city;
        private String street;
        private String zipCode;
    }
}


