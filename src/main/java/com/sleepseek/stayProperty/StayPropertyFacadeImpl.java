package com.sleepseek.stayProperty;

import com.sleepseek.property.PropertyDefinitionFacade;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stayProperty.DTO.StayPropertyDTO;

import java.util.List;
import java.util.stream.Collectors;

class StayPropertyFacadeImpl implements StayPropertyFacade {
    private final StayPropertyRepository stayPropertyRepository;
    private final StayFacade stayFacade;
    private final PropertyDefinitionFacade propertyDefinitionFacade;

    public StayPropertyFacadeImpl(StayPropertyRepository stayPropertyRepository, StayFacade stayFacade, PropertyDefinitionFacade propertyDefinitionFacade) {
        this.stayPropertyRepository = stayPropertyRepository;
        this.stayFacade = stayFacade;
        this.propertyDefinitionFacade = propertyDefinitionFacade;
    }

    @Override
    public StayPropertyDTO add(StayPropertyDTO stayPropertyDTO) {
        return StayPropertyMapper.toDTO(stayPropertyRepository.save(StayProperty.builder()
                .propertyDefinition(propertyDefinitionFacade.loadByName(stayPropertyDTO.getName()))
                .stay(stayFacade.loadStay(stayPropertyDTO.getStayId()))
                .build()));
    }

    @Override
    public void delete(Long id) {
        stayPropertyRepository.deleteById(id);
    }

    @Override
    public List<StayPropertyDTO> findByStay(Long stayId) {
        return stayPropertyRepository.findByStay(stayFacade.loadStay(stayId)).stream().map(StayPropertyMapper::toDTO).collect(Collectors.toList());
    }
}
