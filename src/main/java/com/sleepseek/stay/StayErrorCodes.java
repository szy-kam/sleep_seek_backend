package com.sleepseek.stay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StayErrorCodes {
    ID_NULL("id is null"),
    ID_SHOULD_NOT_DEFINED("id should not be defined"),
    NAME_NULL("name is null"),
    USERNAME_NULL("username is null"),
    DESCRIPTION_NULL("description is null"),
    CATEGORY_NULL("category is null"),
    ADDRESS_NULL("address is null"),
    CITY_NULL("city is null"),
    STREET_NULL("street null"),
    ZIPCODE_NULL("zipCode is null"),
    COUNTRY_NULL("country is null"),
    MIN_PRICE_NULL("minPrice is null"),
    EMAIL_NULL("email is null"),
    EMAIL_INVALID("email is in invalid format"),
    PHONE_NUMBER_NULL("phone number is null"),
    PHOTOS_NULL("photos list is null"),
    PROPERTIES_NULL("properties are null")
    ;
    @Getter
    private final String message;
}
