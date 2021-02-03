package com.sleepseek.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReservationStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    DECLINED("DECLINED"),
    INVALID("INVALID"),
    COMPLETED("COMPLETED");

    @Getter
    private final String name;
}
