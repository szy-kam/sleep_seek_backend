package com.sleepseek.property;

import com.sleepseek.property.DTO.PropertyDefinitionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PropertyDefinitionController {
    private final PropertyDefinitionFacade propertyDefinitionFacade;

    public PropertyDefinitionController(PropertyDefinitionFacade propertyDefinitionFacade) {
        this.propertyDefinitionFacade = propertyDefinitionFacade;
    }

    @GetMapping("/propertyDefinition/{type}")
    List<PropertyDefinitionDTO> getByType(String type){
        return propertyDefinitionFacade.findByType(type);
    }

    @PostMapping("/propertyDefinition")
    PropertyDefinitionDTO create(PropertyDefinitionDTO propertyDefinitionDTO){
        return propertyDefinitionFacade.add(propertyDefinitionDTO);
    }

    @PutMapping("/propertyDefinition/{id}")
    PropertyDefinitionDTO update(@PathVariable Long id, PropertyDefinitionDTO propertyDefinitionDTO){
        return propertyDefinitionFacade.update(id, propertyDefinitionDTO);
    }

    @DeleteMapping("/propertyDefinition/{id}")
    void delete(@PathVariable Long id){
        propertyDefinitionFacade.delete(id);
    }

    @GetMapping("/propertyDefinition/{id}")
    PropertyDefinitionDTO read(@PathVariable Long id){
        return PropertyDefinitionMapper.toDTO(propertyDefinitionFacade.loadById(id));
    }
}
