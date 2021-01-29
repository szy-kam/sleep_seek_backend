package com.sleepseek.stay.exception;

public class StayPropertyNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Stay property not found with given ID: ";

    public StayPropertyNotFoundException(String property) {
        super(MESSAGE + property);
    }
}
