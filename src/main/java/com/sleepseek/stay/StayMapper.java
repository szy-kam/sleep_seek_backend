package com.sleepseek.stay;

import com.sleepseek.image.Image;
import com.sleepseek.review.Review;
import com.sleepseek.stay.DTO.StayDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
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
                .avgRate(countAvgRating(stay))
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

    private static String countAvgRating(Stay stay) {
        if (stay.getReviews() == null || stay.getReviews().size() == 0) {
            return "0";
        }
        Double sum = 0.0;
        for (Review review : stay.getReviews()) {
            sum += review.getRating();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        return decimalFormat.format(sum / (double) stay.getReviews().size());
    }
}
