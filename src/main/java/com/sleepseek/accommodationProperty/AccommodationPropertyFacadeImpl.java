package com.sleepseek.accommodationProperty;

import com.sleepseek.accommodationProperty.DTO.AccommodationPropertyDTO;
import com.sleepseek.accomodation.AccommodationFacade;
import com.sleepseek.property.PropertyDefinitionFacade;

import java.util.List;
import java.util.stream.Collectors;

public class AccommodationPropertyFacadeImpl implements AccommodationPropertyFacade {
    private final AccommodationPropertyRepository accommodationPropertyRepository;
    private final PropertyDefinitionFacade propertyDefinitionFacade;
    private final AccommodationFacade accommodationFacade;

    public AccommodationPropertyFacadeImpl(AccommodationPropertyRepository accommodationPropertyRepository, PropertyDefinitionFacade propertyDefinitionFacade, AccommodationFacade accommodationFacade) {
        this.accommodationPropertyRepository = accommodationPropertyRepository;
        this.propertyDefinitionFacade = propertyDefinitionFacade;
        this.accommodationFacade = accommodationFacade;
    }


    @Override
    public AccommodationPropertyDTO add(AccommodationPropertyDTO accommodationPropertyDTO) {
        return AccommodationPropertyMapper.toDTO(accommodationPropertyRepository.save(AccommodationProperty.builder()
                .accommodation(accommodationFacade.loadById(accommodationPropertyDTO.getAccommodationId()))
                .property(propertyDefinitionFacade.loadByName(accommodationPropertyDTO.getName()))
                .build()));
    }

    @Override
    public void delete(Long id) {
        accommodationPropertyRepository.deleteById(id);
    }

    @Override
    public List<AccommodationPropertyDTO> findByAccommodation(Long accommodationId) {
        return accommodationPropertyRepository.findByAccommodation(accommodationFacade.loadById(accommodationId)).stream()
                .map(AccommodationPropertyMapper::toDTO).collect(Collectors.toList());
    }
}
