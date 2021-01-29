package com.sleepseek.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReviewErrorCodes {
    ID_NULL("id is null"),
    MESSAGE_NULL("message is null"),
    RATING_NULL("rating is null"),
    RATING_OUT_OF_BOUNDS("rating is out of bounds"),
    USER_NULL("user is null"),
    STAY_NULL("stay is null");

    @Getter
    private final String message;
}
