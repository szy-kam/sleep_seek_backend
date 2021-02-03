package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;
import org.springframework.data.domain.PageRequest;
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
    List<ReservationDTO> getReservations(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long stayId) {

        if (!isNull(username)) {
            return reservationFacade.getReservationsByUsername(username, PageRequest.of(pageNumber, pageSize));
        }

        if (!isNull(accommodationId)) {
            return reservationFacade.getReservationsByAccommodationId(accommodationId, PageRequest.of(pageNumber, pageSize));
        }

        if (!isNull(stayId)) {
            return reservationFacade.getReservationsByStayId(stayId, PageRequest.of(pageNumber, pageSize));
        }
        return null;
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
    void postReservation(@RequestParam Long accommodationTemplateId, Principal principal, @RequestBody ReservationDTO reservationDTO) {
        reservationDTO.getCustomer().setUsername(principal.getName());
        reservationFacade.addReservation(accommodationTemplateId, reservationDTO);
    }

    @PutMapping("/reservation/{id}")
    void updateReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationFacade.updateReservation(reservationDTO);
    }
}
