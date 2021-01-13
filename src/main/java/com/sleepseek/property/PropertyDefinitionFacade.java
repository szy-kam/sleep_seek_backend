package com.sleepseek.property;

import com.sleepseek.property.DTO.PropertyDefinitionDTO;

import java.util.List;

public interface PropertyDefinitionFacade {


    List<PropertyDefinitionDTO> findByType(String type);

    PropertyDefinitionDTO add(PropertyDefinitionDTO propertyDefinitionDTO);

    PropertyDefinitionDTO update(Long id, PropertyDefinitionDTO propertyDefinitionDTO);

    PropertyDefinition loadById(Long id);

    void delete(Long id);

    PropertyDefinition loadByName(String name);
}
