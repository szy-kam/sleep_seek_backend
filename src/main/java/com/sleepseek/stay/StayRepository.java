package com.sleepseek.stay;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface StayRepository extends Repository<Stay, Long> {
    Optional<Stay> findById(Long id);

    boolean existsById(Long id);

    Stay save(Stay stay);

    List<Stay> findInRange(Integer from, Integer to);
}
