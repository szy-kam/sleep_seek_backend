package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StaySearchParametersErrorCodes {
    WRONG_PAGE_CONSTRAINTS("Wrong page constraints");

    @Getter
    private final String message;
}
