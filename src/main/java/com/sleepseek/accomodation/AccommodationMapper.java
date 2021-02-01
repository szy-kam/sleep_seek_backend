package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.DTO.AccommodationTemplateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class AccommodationMapper {

    public static AccommodationTemplateDTO toDTO(AccommodationTemplate accommodationTemplate) {
        List<String> properties;
        if (isNull(accommodationTemplate.getProperties())) {
            properties = new ArrayList<>();
        } else {
            properties = accommodationTemplate.getProperties().stream().map(AccommodationProperty::getName).collect(Collectors.toList());
        }

        return AccommodationTemplateDTO.builder()
                .id(accommodationTemplate.getId())
                .price(accommodationTemplate.getPrice())
                .quantity(accommodationTemplate.getQuantity())
                .sleepersCapacity(accommodationTemplate.getSleepersCapacity())
                .stayId(accommodationTemplate.getStay().getId())
                .properties(properties)
                .prefix(accommodationTemplate.getPrefix())
                .build();
    }

    public static AccommodationDTO toDTO(Accommodation accommodation) {
        return AccommodationDTO.builder()
                .accommodationTemplateId(accommodation.getAccommodationTemplate().getId())
                .alias(accommodation.getAlias())
                .id(accommodation.getId())
                .build();
    }

}
