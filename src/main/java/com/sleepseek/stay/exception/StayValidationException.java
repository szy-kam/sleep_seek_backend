package com.sleepseek.stay.exception;

import com.sleepseek.stay.StayErrorCodes;

import java.util.Set;

public class StayValidationException extends RuntimeException {
    public static final String MESSAGE_PREFIX = "Invalid user data: ";

    public StayValidationException(Set<StayErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(StayErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }
}
