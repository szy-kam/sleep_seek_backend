package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReservationFacade {

    void addReservation(Long accommodationTemplateId, ReservationDTO reservation);

    void deleteReservation(Long id);

    void updateReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> getReservationsByAccommodationId(Long accommodationId, PageRequest of);
    List<ReservationDTO> getReservationsByStayId(Long stayId, PageRequest of);

    ReservationDTO getReservation(Long id);

    List<ReservationDTO> getReservationsByUsername(String username, PageRequest of);
}
