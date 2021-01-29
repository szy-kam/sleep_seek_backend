package com.sleepseek.user.exception;

import com.sleepseek.user.UserErrorCodes;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class UserValidationException extends RuntimeException {
    public static final String MESSAGE_PREFIX = "Invalid user data: ";

    public UserValidationException(Set<UserErrorCodes> errorCodes) {
        super(MESSAGE_PREFIX + errorCodes.stream().map(UserErrorCodes::getMessage).reduce((x, y) -> x + ", " + y).orElse(""));
    }

}
