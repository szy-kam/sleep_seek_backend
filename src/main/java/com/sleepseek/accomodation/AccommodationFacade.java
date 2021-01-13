package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;

import java.util.List;

public interface AccommodationFacade {

    AccommodationDTO addAccommodation(AccommodationDTO accommodationDTO);

    void deleteAccommodation(Long id);

    List<AccommodationDTO> getAccommodationsByStay(Long stayId);

    Accommodation loadById(Long id);

    AccommodationDTO updateAccommodation(AccommodationDTO accommodationDTO);

}
