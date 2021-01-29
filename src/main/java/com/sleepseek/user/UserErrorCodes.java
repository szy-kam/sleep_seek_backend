package com.sleepseek.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserErrorCodes {
    USERNAME_IS_NULL("username is null"),
    PASSWORD_IS_NULL("password is null"),
    PASSWORD_IS_TOO_LONG("password is too long"),
    USERNAME_IS_NOT_EMAIL("username is invalid, should be in email format"),
    DISPLAY_NAME_IS_NULL("displayName is null");

    @Getter
    private final String message;
}
