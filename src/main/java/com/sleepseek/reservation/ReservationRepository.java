package com.sleepseek.reservation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomer_User_UsernameOrderByCreatedAtDesc(String user, Pageable of);

    List<Reservation> findAllByAccommodation_AccommodationTemplate_IdOrderByCreatedAtDesc(Long accommodationTemplateId, Pageable of);

    List<Reservation> findAllByAccommodation_AccommodationTemplate_Stay_IdOrderByCreatedAtDesc(Long stayId, Pageable of);
}
