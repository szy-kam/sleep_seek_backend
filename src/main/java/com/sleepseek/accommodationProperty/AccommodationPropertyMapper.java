package com.sleepseek.accommodationProperty;

import com.sleepseek.accommodationProperty.DTO.AccommodationPropertyDTO;

class AccommodationPropertyMapper {

    static AccommodationPropertyDTO toDTO(AccommodationProperty accommodationProperty){
        return AccommodationPropertyDTO.builder()
                .accommodationId(accommodationProperty.getProperty().getId())
                .name(accommodationProperty.getProperty().getName())
                .build();
    }
}
