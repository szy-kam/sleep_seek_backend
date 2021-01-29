package com.sleepseek.accomodation.exception;

public class AccommodationNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Accommodation not found with given ID: ";

    public AccommodationNotFoundException(Long id) {
        super(MESSAGE + id.toString());
    }
}
