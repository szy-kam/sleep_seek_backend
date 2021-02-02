package com.sleepseek.accomodation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findAllByAccommodationTemplate_Id(Long id, Pageable of);
}
