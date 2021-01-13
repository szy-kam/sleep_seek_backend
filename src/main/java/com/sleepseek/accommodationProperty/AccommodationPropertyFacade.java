package com.sleepseek.accommodationProperty;

import com.sleepseek.accommodationProperty.DTO.AccommodationPropertyDTO;

import java.util.List;

public interface AccommodationPropertyFacade {

    AccommodationPropertyDTO add(AccommodationPropertyDTO accommodationPropertyDTO);

    void delete(Long id);

    List<AccommodationPropertyDTO> findByAccommodation(Long accommodationId);

}
