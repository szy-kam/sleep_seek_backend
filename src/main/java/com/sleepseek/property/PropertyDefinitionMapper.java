package com.sleepseek.property;

import com.sleepseek.property.DTO.PropertyDefinitionDTO;

class PropertyDefinitionMapper {

    static PropertyDefinitionDTO toDTO(PropertyDefinition propertyDefinition){
        return PropertyDefinitionDTO.builder()
                .ico(propertyDefinition.getIco())
                .name(propertyDefinition.getName())
                .id(propertyDefinition.getId())
                .type(propertyDefinition.getType())
                .build();
    }
}
