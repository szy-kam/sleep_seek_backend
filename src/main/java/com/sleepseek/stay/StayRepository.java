package com.sleepseek.stay;

import java.util.List;
import java.util.Optional;

interface StayRepository {
    Optional<Stay> findById(Long id);

    boolean existsById(Long id);

    Stay save(Stay stay);

    List<Stay> findInRange(Long from, Long to);
}
