package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StaySearchParametersErrorCodes {
    WRONG_PAGE_CONSTRAINTS("Wrong page constraints"),
    CATEGORY_NOT_FOUND("category not found"),
    PROPERTY_NOT_FOUND("property not found"),
    PRICE_OUT_OF_BOUNDS("priceTo/priceFrom out of bounds"),
    DATES_OUT_OF_BOUNDS("dates out of bounds"),
    ORDER_BY_INVALID("order by invalid value"),
    ORDER_INVALID("order invalid value")
    ;

    @Getter
    private final String message;
}
