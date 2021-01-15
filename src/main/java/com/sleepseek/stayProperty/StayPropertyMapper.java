package com.sleepseek.stayProperty;

import com.sleepseek.stayProperty.DTO.StayPropertyDTO;

public class StayPropertyMapper {

    static StayPropertyDTO toDTO(StayProperty stayProperty){
        return StayPropertyDTO.builder()
                .stayId(stayProperty.getStay().getId())
                .name(stayProperty.getPropertyDefinition().getName())
                .build();
    }
}
