package com.sleepseek.stay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

interface StayRepositoryAdapter {
    boolean existsById(Long id);

    List<Stay> findAllByParameters(StaySearchParameters parameters);

    Page<Stay> findAllByUser_Username(String username, Pageable pageable);

    Stay save(Stay stay);

    Optional<Stay> findById(Long id);

    void deleteById(Long id);
}
