package com.sleepseek.stay.exception;

public class StayAlreadyExistsException extends RuntimeException {
    private final static String MESSAGE = "Stay already exists with given ID: ";


    public StayAlreadyExistsException(Long id) {
        super(MESSAGE + id.toString());
    }
}
