package com.sleepseek.accomodation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccommodationErrorCodes {
    ID_NULL("id is null"),
    STAY_NULL("stayId is null"),
    SLEEPERS_CAP_NULL("sleepersCapacity is null"),
    SLEEPERS_CAP_BOUNDARIES("sleepersCapacity out of boundaries"),
    QUANTITY_BOUNDARIES("quantity out of boundaries"),
    QUANTITY_NULL("quantity is null"),
    PROPERTIES_NULL("properties are null"),
    PRICE_OUT_OF_BOUNDS("price is out of bounds"),
    PRICE_NULL("price is null");

    @Getter
    private final String message;
}
