package com.sleepseek.stay.DTO;

import lombok.*;

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

    private String mainPhoto;

    private String createdAt;

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


