package com.sleepseek.reservation;

import com.google.common.collect.Sets;
import com.sleepseek.reservation.DTO.ReservationDTO;
import com.sleepseek.reservation.exception.ReservationValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;

import static com.sleepseek.reservation.ReservationErrorCodes.*;
import static java.util.Objects.isNull;

class ReservationValidation {
    void validateReservation(ReservationDTO reservationDTO, boolean checkId) {
        Set<ReservationErrorCodes> errors = Sets.newHashSet();
        if (checkId) {
            checkId(reservationDTO.getId()).ifPresent(errors::add);
            checkAccommodation(reservationDTO.getAccommodationId()).ifPresent(errors::add);
        }
        checkDateFrom(reservationDTO.getDateFrom()).ifPresent(errors::add);
        checkDateTo(reservationDTO.getDateTo()).ifPresent(errors::add);
        checkDates(reservationDTO.getDateFrom(), reservationDTO.getDateTo()).ifPresent(errors::add);
        checkCustomer(reservationDTO.getCustomer()).ifPresent(errors::add);
        checkStatus(reservationDTO.getStatus()).ifPresent(errors::add);
        if (!errors.isEmpty()) {
            throw new ReservationValidationException(errors);
        }
    }

    private Optional<ReservationErrorCodes> checkDates(String dateFromParam, String dateToParam) {
        if (isNull(dateFromParam) || isNull(dateToParam)) {
            return Optional.of(DATE_INVALID_FORMAT);
        }
        try {
            LocalDate dateFrom = LocalDate.parse(dateFromParam);
            LocalDate dateTo = LocalDate.parse(dateToParam);
            if (dateFrom.isAfter(dateTo)) {
                return Optional.of(DATE_INVALID);
            }
        } catch (DateTimeParseException e) {
            return Optional.of(DATE_INVALID_FORMAT);
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkStatus(String status) {
        if (!isNull(status)) {
            try {
                ReservationStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                return Optional.of(STATUS_INVALID);
            }
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkAccommodation(Long accommodationId) {
        if (isNull(accommodationId)) {
            return Optional.of(ACCOMMODATION_NULL);
        }

        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkCustomer(ReservationDTO.CustomerDTO customer) {
        if (isNull(customer)) {
            return Optional.of(CUSTOMER_NULL);
        }
        if (isNull(customer.getUsername())) {
            return Optional.of(CUSTOMER_NULL);
        }

        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkDateFrom(String dateFrom) {
        if (isNull(dateFrom)) {
            return Optional.of(DATE_FROM_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkDateTo(String dateTo) {
        if (isNull(dateTo)) {
            return Optional.of(DATE_TO_NULL);
        }
        return Optional.empty();
    }

    private Optional<ReservationErrorCodes> checkId(Long id) {
        if (isNull(id)) {
            return Optional.of(ID_NULL);
        }
        return Optional.empty();
    }
}
