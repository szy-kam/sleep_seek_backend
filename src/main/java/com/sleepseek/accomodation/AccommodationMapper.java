package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;

class AccommodationMapper {

    static AccommodationDTO toDTO(Accommodation accommodation){
        return AccommodationDTO.builder()
                .id(accommodation.getId())
                .price(accommodation.getPrice())
                .quantity(accommodation.getQuantity())
                .sleepersCapacity(accommodation.getSleepersCapacity())
                .stayId(accommodation.getStay().getId())
                .build();
    }

}
