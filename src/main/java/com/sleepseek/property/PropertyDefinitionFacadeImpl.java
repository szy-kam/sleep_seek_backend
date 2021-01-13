package com.sleepseek.property;

import com.sleepseek.property.DTO.PropertyDefinitionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PropertyDefinitionFacadeImpl implements PropertyDefinitionFacade {
    private final PropertyDefinitionRepository propertyDefinitionRepository;

    public PropertyDefinitionFacadeImpl(PropertyDefinitionRepository propertyDefinitionRepository) {
        this.propertyDefinitionRepository = propertyDefinitionRepository;
    }

    @Override
    public List<PropertyDefinitionDTO> findByType(String type) {
        return propertyDefinitionRepository.findPropertyDefinitionsByType(type).stream().map(PropertyDefinitionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public PropertyDefinitionDTO add(PropertyDefinitionDTO propertyDefinitionDTO) {
        return PropertyDefinitionMapper.toDTO(propertyDefinitionRepository.save(
                PropertyDefinition.builder()
                        .ico(propertyDefinitionDTO.getIco())
                        .name(propertyDefinitionDTO.getName())
                        .type(propertyDefinitionDTO.getType())
                        .build()
        ));
    }

    @Override
    public PropertyDefinitionDTO update(Long id, PropertyDefinitionDTO propertyDefinitionDTO) {
        PropertyDefinition propertyDefinition = propertyDefinitionRepository.findById(id).orElseThrow();
        propertyDefinition.setIco(propertyDefinitionDTO.getIco());
        propertyDefinition.setName(propertyDefinitionDTO.getName());
        propertyDefinition.setType(propertyDefinitionDTO.getType());
        return PropertyDefinitionMapper.toDTO(propertyDefinitionRepository.save(propertyDefinition));
    }

    @Override
    public PropertyDefinition loadById(Long id) {
        return propertyDefinitionRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        propertyDefinitionRepository.deleteById(id);
    }

    @Override
    public PropertyDefinition loadByName(String name) {
        return propertyDefinitionRepository.findByName(name);
    }
}
