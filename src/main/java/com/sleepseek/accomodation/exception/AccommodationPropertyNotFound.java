package com.sleepseek.accomodation.exception;

public class AccommodationPropertyNotFound extends RuntimeException {
    private final static String MESSAGE = "Property not found with given ID: ";

    public AccommodationPropertyNotFound(String id) {
        super(MESSAGE + id.toString());
    }
}
