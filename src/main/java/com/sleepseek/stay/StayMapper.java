package com.sleepseek.stay;

import com.sleepseek.stay.DTO.StayDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class StayMapper {
    static StayDTO toDto(Stay stay){
        return StayDTO.builder()
                .id(stay.getId())
                .name(stay.getName())
                .contactInfo(stay.getContactInfo())
                .createdAt(stay.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .description(stay.getDescription())
                .mainPhoto(stay.getMainPhoto())
                .price(stay.getPrice())
                .properties(stay.getProperties())
                .userId(stay.getUserId())
                .address(StayDTO.AddressDTO.builder()
                        .city(stay.getAddress().getCity())
                        .address(stay.getAddress().getAddress())
                        .zipCode(stay.getAddress().getZipCode())
                        .build())
                .build();
    }
}
