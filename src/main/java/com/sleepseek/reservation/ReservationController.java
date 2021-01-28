package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
public class ReservationController {

    private final ReservationFacade reservationFacade;

    public ReservationController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping("/reservation")
    List<ReservationDTO> getReservationsByAccommodation(@RequestParam(required = false) Long accommodationId, @RequestParam(required = false) String username) {
        if (isNull(username) && !isNull(accommodationId)) {
            return reservationFacade.getReservationsByAccommodationId(accommodationId);
        } else if (isNull(accommodationId) && !isNull(username)) {
            return reservationFacade.getReservationsByUsername(username);
        } else {
            return null;
        }
    }


    @GetMapping("/reservation/{id}")
    ReservationDTO getReservation(@PathVariable Long id) {
        return reservationFacade.getReservation(id);
    }

    @DeleteMapping("/reservation/{id}")
    void delete(@PathVariable Long id) {
        reservationFacade.deleteReservation(id);
    }

    @PostMapping("/reservation")
    void postReservation(Principal principal, @RequestBody ReservationDTO reservationDTO) {
        reservationDTO.getCustomer().setUsername(principal.getName());
        reservationFacade.addReservation(reservationDTO);
    }

    @PutMapping("/reservation/{id}")
    void updateReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationFacade.updateReservation(reservationDTO);
    }
}
