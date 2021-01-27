package com.sleepseek.reservation.exception;

import com.sleepseek.reservation.ReservationErrorCodes;

import java.util.Set;

public class ReservationValidationException extends RuntimeException {
    public static final String MESSAGE_PREFIX = "Invalid reservation data: ";

    public ReservationValidationException(Set<ReservationErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(ReservationErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }
}
