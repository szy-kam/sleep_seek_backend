package com.sleepseek.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomer_User_Username(String user);
    List<Reservation> findAllByAccommodation_AccommodationTemplate_Id(Long accommodationTemplateId);
    List<Reservation> findAllByAccommodation_AccommodationTemplate_Stay_Id(Long stayId);
}
