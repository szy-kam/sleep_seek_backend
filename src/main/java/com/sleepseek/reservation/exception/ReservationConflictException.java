package com.sleepseek.reservation.exception;

public class ReservationConflictException extends RuntimeException {
    private final static String MESSAGE = "Reservation conflict dates: ";

    public ReservationConflictException(String dateFrom, String dateTo) {
        super(MESSAGE + dateFrom + dateTo);
    }
}
