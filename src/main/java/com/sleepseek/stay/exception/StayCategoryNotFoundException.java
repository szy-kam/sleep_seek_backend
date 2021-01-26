package com.sleepseek.stay.exception;

public class StayCategoryNotFoundException extends RuntimeException {
    private final static String MESSAGE = "Stay category not found with given ID: ";

    public StayCategoryNotFoundException(String category) {
        super(MESSAGE + category);
    }
}
