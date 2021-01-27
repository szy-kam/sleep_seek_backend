package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StaySearchParametersErrorCodes {
    WRONG_PAGE_CONSTRAINTS("Wrong page constraints"),
    CATEGORY_NOT_FOUND("category not found"),
    PROPERTY_NOT_FOUND("property not found");

    @Getter
    private final String message;
}
