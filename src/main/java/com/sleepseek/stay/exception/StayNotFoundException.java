package com.sleepseek.stay.exception;

public class StayNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Stay not found with given ID: ";

    public StayNotFoundException(Long id) {
        super(MESSAGE + id.toString());
    }
}
