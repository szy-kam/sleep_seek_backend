package com.sleepseek.stay;

import com.sleepseek.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
    Page<Stay> findAllByUser(User username, Pageable pageable);
}
