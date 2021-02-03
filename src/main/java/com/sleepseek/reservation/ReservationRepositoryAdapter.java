package com.sleepseek.reservation;

import com.sleepseek.accomodation.Accommodation;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryAdapter {
    List<Reservation> findAllByCustomer_User_Username(String user, Pageable of);

    List<Reservation> findAllByAccommodation_AccommodationTemplate_Id(Long accommodationTemplateId, Pageable of);

    List<Reservation> findAllByAccommodation_AccommodationTemplate_Stay_Id(Long stayId, Pageable of);

    Reservation save(Reservation newReservation);

    boolean existsById(Long id);

    Reservation getOne(Long id);

    void deleteById(Long id);

    boolean isReservable(Long accommodationTemplateId, LocalDate dateFrom, LocalDate dateTo);

    Accommodation getReservable(Long accommodationTemplateId, LocalDate parse, LocalDate parse1);
}
