package com.sleepseek.review;

import com.sleepseek.stay.Stay;
import com.sleepseek.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStay(Stay stay);
}
