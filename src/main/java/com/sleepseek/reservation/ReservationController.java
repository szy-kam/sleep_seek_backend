package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationFacade reservationFacade;

    public ReservationController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping("/reservation")
    List<ReservationDTO> getReservationsByAccommodation(@RequestParam Long accommodationId) {
        return reservationFacade.getReservationsByAccommodationId(accommodationId);
    }

    @GetMapping("/reservation/{id}")
    ReservationDTO getReservation(@PathVariable Long id){
        return reservationFacade.getReservation(id);
    }

    @DeleteMapping("/reservation/{id}")
    void delete(@PathVariable Long id) {
        reservationFacade.deleteReservation(id);
    }

    @PostMapping("/reservation")
    void postReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationFacade.addReservation(reservationDTO);
    }

    @PutMapping("/reservation/{id}")
    void updateReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationFacade.updateReservation(reservationDTO);
    }
}
