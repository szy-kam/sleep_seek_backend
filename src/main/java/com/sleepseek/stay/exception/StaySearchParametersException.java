package com.sleepseek.stay.exception;

import com.sleepseek.stay.StayErrorCodes;
import com.sleepseek.stay.StaySearchParametersErrorCodes;

import java.util.Set;

public class StaySearchParametersException extends RuntimeException {
    private static final Object MESSAGE_PREFIX = "Search parameters invalid: ";

    public StaySearchParametersException(Set<StaySearchParametersErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(StaySearchParametersErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }
}
