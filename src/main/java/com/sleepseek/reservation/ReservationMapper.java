package com.sleepseek.reservation;

import com.sleepseek.reservation.DTO.ReservationDTO;

import java.time.format.DateTimeFormatter;

public class ReservationMapper {
    static ReservationDTO toDto(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .completed(reservation.getCompleted())
                .confirmed(reservation.getConfirmed())
                .dateFrom(reservation.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .dateTo(reservation.getDateTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .accommodationId(reservation.getAccommodation().getId())
                .createdAt(reservation.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .customer(ReservationDTO.CustomerDTO.builder()
                        .username(reservation.getCustomer().getUser().getUsername())
                        .phoneNumber(reservation.getCustomer().getPhoneNumber())
                        .build())
                .build();
    }
}
