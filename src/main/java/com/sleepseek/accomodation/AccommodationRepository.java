package com.sleepseek.accomodation;

import com.sleepseek.stay.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findAccommodationsByStay(Stay stay);
}
