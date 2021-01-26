package com.sleepseek.accomodation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccommodationPropertyDefinition {
    BATH("BATH"),
    SHOWER("SHOWER"),
    USER_PLUS("USER_PLUS"),
    SMOKING("SMOKING"),
    COOLING("COOLING");

    @Getter
    private final String name;
}
