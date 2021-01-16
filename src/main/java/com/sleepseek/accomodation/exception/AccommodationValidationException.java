package com.sleepseek.accomodation.exception;

import com.sleepseek.accomodation.AccommodationErrorCodes;

import java.util.Set;

public class AccommodationValidationException extends RuntimeException {
    public static final String MESSAGE_PREFIX = "Invalid user data: ";

    public AccommodationValidationException(Set<AccommodationErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(AccommodationErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }
}
