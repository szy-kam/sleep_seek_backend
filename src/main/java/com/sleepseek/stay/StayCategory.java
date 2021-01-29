package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum StayCategory {
    HOTEL("HOTEL"),
    PENSION("PENSION"),
    HOSTEL("HOSTEL"),
    HOUSE("HOUSE"),
    APARTMENT("APARTMENT"),
    FLAT("FLAT"),
    PRIVATE_FLAT("PRIVATE_FLAT"),
    SHELTER("SHELTER"),
    RESORT("RESORT")
    ;

    @Getter
    private final String name;
}
