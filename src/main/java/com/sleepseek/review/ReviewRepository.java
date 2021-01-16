package com.sleepseek.review;

import com.sleepseek.stay.Stay;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStay(Stay stay, Pageable pageable);
}
