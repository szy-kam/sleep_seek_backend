package com.sleepseek.image.exception;

import com.sleepseek.image.ImageErrorCodes;

import java.util.Set;

public class ImageValidationException extends RuntimeException {
    public static final String MESSAGE_PREFIX = "Invalid user data: ";

    public ImageValidationException(Set<ImageErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(ImageErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }
}
