package com.sleepseek.accomodation;

import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.DTO.AccommodationTemplateDTO;
import com.sleepseek.reservation.Reservation;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AccommodationFacade {

    void addAccommodationTemplate(AccommodationTemplateDTO accommodationTemplateDTO);

    void deleteAccommodationTemplate(Long id);

    List<AccommodationTemplateDTO> getAccommodationTemplatesByStay(Long stayId, PageRequest of);

    AccommodationTemplate loadAccommodationTemplateById(Long id);
    Accommodation loadAccommodationById(Long id);

    void updateAccommodationTemplate(AccommodationTemplateDTO accommodationTemplateDTO);

    AccommodationTemplateDTO getAccommodationTemplate(Long id);

    List<AccommodationDTO> getAccommodations(Long accommodationTemplateId, PageRequest of);

    AccommodationDTO getAccommodation(Long id);

    void updateAccommodation(AccommodationDTO accommodationDTO);

    boolean accommodationTemplateExistsById(Long id);

    void addReservation(Accommodation accommodation, Reservation newReservation);

    List<AccommodationDTO> getAccommodationsByDate(Long accommodationTemplateId, PageRequest of, String dateFrom, String dateTo);
}
