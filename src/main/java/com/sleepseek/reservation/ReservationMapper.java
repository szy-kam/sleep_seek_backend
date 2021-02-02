package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;

import java.time.format.DateTimeFormatter;

public class ReservationMapper {
    static ReservationDTO toDto(Reservation reservation) {
        Long stayId = null;
        Long accommodationId = null;
        if(reservation.getAccommodation() != null){
            stayId = reservation.getAccommodation().getAccommodationTemplate().getStay().getId();
            accommodationId = reservation.getAccommodation().getId();
        }
        return ReservationDTO.builder()
                .id(reservation.getId())
                .stayId(stayId)
                .dateFrom(reservation.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .dateTo(reservation.getDateTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .accommodationId(accommodationId)
                .status(reservation.getStatus().getName())
                .createdAt(reservation.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .customer(ReservationDTO.CustomerDTO.builder()
                        .username(reservation.getCustomer().getUser().getUsername())
                        .phoneNumber(reservation.getCustomer().getPhoneNumber())
                        .fullName(reservation.getCustomer().getFullName())
                        .build())
                .build();
    }
}
