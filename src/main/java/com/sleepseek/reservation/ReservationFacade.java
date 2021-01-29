package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationFacade {

    void addReservation(ReservationDTO reservation);

    void deleteReservation(Long id);

    void updateReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> getReservationsByAccommodationId(Long accommodationId);
    List<ReservationDTO> getReservationsByStayId(Long stayId);

    ReservationDTO getReservation(Long id);

    List<ReservationDTO> getReservationsByUsername(String username);
}
