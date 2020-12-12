package com.sleepseek.stay;

import com.sleepseek.image.Image;
import com.sleepseek.image.ImageMapper;
import com.sleepseek.stay.DTO.StayDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class StayMapper {
    static StayDTO toDto(Stay stay) {
        return StayDTO.builder()
                .id(stay.getId())
                .name(stay.getName())
                .phoneNumber(stay.getPhoneNumber())
                .email(stay.getEmail())
                .createdAt(stay.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .description(stay.getDescription())
                .mainPhoto(stay.getMainPhoto())
                .minPrice(stay.getMinPrice())
                .userId(stay.getUserId())
                .address(StayDTO.AddressDTO.builder()
                        .city(stay.getAddress().getCity())
                        .street(stay.getAddress().getStreet())
                        .zipCode(stay.getAddress().getZipCode())
                        .build())
                .photos(stay.getPhotos().stream().map(ImageMapper::toDto).collect(Collectors.toList()))
                .build();
    }
}
