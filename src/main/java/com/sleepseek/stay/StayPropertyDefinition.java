package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum StayPropertyDefinition {
    PARKING("PARKING"),
    GYM("GYM"),
    POOL("POOL"),
    RESTAURANT("RESTAURANT"),
    WIFI("WIFI"),
    RECEPTION24H("RECEPTION24H"),
    PETS_ALLOWED("PETS_ALLOWED"),
    CARD_ACCEPTED("CARD_ACCEPTED"),
    DISABLED_ACCESSIBLE("DISABLED_ACCESSIBLE"),
    BAR("BAR")
    ;

    @Getter
    private final String name;
}