package com.sleepseek.accomodation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface AccommodationTemplateRepository extends JpaRepository<AccommodationTemplate, Long> {
    List<AccommodationTemplate> findAllByStayId(Long id, Pageable page);
}
