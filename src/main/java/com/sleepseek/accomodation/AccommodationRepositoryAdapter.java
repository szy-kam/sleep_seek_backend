package com.sleepseek.accomodation;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccommodationRepositoryAdapter {

    List<Accommodation> findAllByAccommodationTemplate_Id(Long id, Pageable of);

    List<Accommodation> findAllReservable(Long accommodationTemplateId, LocalDate from, LocalDate to);

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<Accommodation> findById(Long id);

    Accommodation getOne(Long id);

    Accommodation save(Accommodation accommodation);
}
