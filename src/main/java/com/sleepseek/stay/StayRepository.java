package com.sleepseek.stay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface StayRepository extends JpaRepository<Stay, Long> {
    Page<Stay> findAllByUser_Username(String username, Pageable pageable);
}
