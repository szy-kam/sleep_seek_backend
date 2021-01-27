package com.sleepseek.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReservationErrorCodes {
    ID_NULL("id is null"),
    ACCOMMODATION_NULL("accommodation is null"),
    DATE_FROM_NULL("date from is null"),
    DATE_TO_NULL("date to is null"),
    CUSTOMER_NULL("customer is null");

    @Getter
    private final String message;
}
