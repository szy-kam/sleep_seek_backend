package com.sleepseek.stay;

import com.sleepseek.accomodation.AccommodationMapper;
import com.sleepseek.image.Image;
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
                .category(stay.getCategory().getName())
                .username(stay.getUser().getUsername())
                .properties(stay.getProperties().stream().map(StayProperty::getName).collect(Collectors.toList()))
                .accommodations(stay.getAccommodations().stream().map(AccommodationMapper::toDTO).collect(Collectors.toList()))
                .address(StayDTO.AddressDTO.builder()
                        .city(stay.getAddress().getCity())
                        .street(stay.getAddress().getStreet())
                        .zipCode(stay.getAddress().getZipCode())
                        .country(stay.getAddress().getCountry())
                        .longitude(stay.getAddress().getLongitude())
                        .latitude(stay.getAddress().getLatitude())
                        .build())
                .photos(stay.getPhotos().stream().map(Image::getUrl).collect(Collectors.toList()))
                .build();
    }
}
