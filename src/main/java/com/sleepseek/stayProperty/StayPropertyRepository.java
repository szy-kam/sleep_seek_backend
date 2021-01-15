package com.sleepseek.stayProperty;

import com.sleepseek.stay.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayPropertyRepository extends JpaRepository<StayProperty, Long> {
    List<StayProperty> findByStay(Stay stay);
}
