package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class AccommodationMapper {

    public static AccommodationDTO toDTO(Accommodation accommodation) {
        List<String> properties;
        if (isNull(accommodation.getProperties())) {
            properties = new ArrayList<>();
        } else {
            properties = accommodation.getProperties().stream().map(AccommodationProperty::getName).collect(Collectors.toList());
        }

        return AccommodationDTO.builder()
                .id(accommodation.getId())
                .price(accommodation.getPrice())
                .quantity(accommodation.getQuantity())
                .sleepersCapacity(accommodation.getSleepersCapacity())
                .stayId(accommodation.getStay().getId())
                .properties(properties)
                .build();
    }

}
