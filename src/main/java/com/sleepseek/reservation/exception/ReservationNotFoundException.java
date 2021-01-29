package com.sleepseek.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Reservation not found with given ID: ";

    public ReservationNotFoundException(Long id) {
        super(MESSAGE + id.toString());
    }
}
