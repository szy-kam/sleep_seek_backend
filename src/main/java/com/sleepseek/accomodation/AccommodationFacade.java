package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.reservation.Reservation;

import java.util.List;

public interface AccommodationFacade {

    AccommodationDTO addAccommodation(AccommodationDTO accommodationDTO);

    void deleteAccommodation(Long id);

    List<AccommodationDTO> getAccommodationsByStay(Long stayId);

    Accommodation loadById(Long id);

    AccommodationDTO updateAccommodation(AccommodationDTO accommodationDTO);

    AccommodationDTO getAccommodation(Long id);

    void addReservation(Accommodation accommodation, Reservation reservation);

    boolean existsById(Long id);
}
