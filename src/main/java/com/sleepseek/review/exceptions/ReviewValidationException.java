package com.sleepseek.review.exceptions;

import com.sleepseek.review.ReviewErrorCodes;

import java.util.Set;

public class ReviewValidationException extends RuntimeException {
    public static final String MESSAGE_PREFIX = "Invalid review data: ";

    public ReviewValidationException(Set<ReviewErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(ReviewErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }

}
