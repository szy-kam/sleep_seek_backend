package com.sleepseek.reservation;

import com.sleepseek.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomer_User_Username(String user);
}
