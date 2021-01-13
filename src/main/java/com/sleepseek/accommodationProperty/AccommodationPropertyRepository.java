package com.sleepseek.accommodationProperty;

import com.sleepseek.accommodationProperty.DTO.AccommodationPropertyDTO;
import com.sleepseek.accomodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationPropertyRepository extends JpaRepository<AccommodationProperty, Long> {
    List<AccommodationProperty> findByAccommodation(Accommodation loadById);
}
