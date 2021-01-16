package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StayErrorCodes {
    ID_NULL("id is null"),
    NAME_NULL("name is null"),
    USERNAME_NULL("username is null"),
    DESCRIPTION_NULL("description is null"),
    CATEGORY_NULL("category is null"),
    ADDRESS_NULL("address is null"),
    CITY_NULL("city is null"),
    STREET_NULL("street null"),
    ZIPCODE_NULL("zipCode is null"),
    COUNTRY_NULL("country is null"),
    LATITUDE_NULL("latitude is null"),
    LONGITUDE_NULL("longitude is null")
    ;
    @Getter
    private final String message;
}
