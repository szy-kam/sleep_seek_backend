package com.sleepseek.stay;

import com.google.common.collect.Sets;
import com.sleepseek.stay.DTO.StayDTO;
import com.sleepseek.stay.exception.StayCategoryNotFoundException;
import com.sleepseek.stay.exception.StayPropertyNotFoundException;
import com.sleepseek.stay.exception.StayValidationException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sleepseek.stay.StayErrorCodes.*;
import static java.util.Objects.isNull;

class StayValidator {
    void validateStay(StayDTO stayDTO, boolean checkId) {
        Set<StayErrorCodes> errorCodes = Sets.newHashSet();
        checkName(stayDTO.getName()).ifPresent(errorCodes::add);
        checkDescription(stayDTO.getDescription()).ifPresent(errorCodes::add);
        checkUsername(stayDTO.getUsername()).ifPresent(errorCodes::add);
        checkAddress(stayDTO.getAddress()).ifPresent(errorCodes::add);
        checkEmail(stayDTO.getEmail()).ifPresent(errorCodes::add);
        checkPhoneNumber(stayDTO.getPhoneNumber()).ifPresent(errorCodes::add);
        checkCategory(stayDTO.getCategory()).ifPresent(errorCodes::add);
        checkMinPrice(stayDTO.getMinPrice()).ifPresent(errorCodes::add);
        checkPhotos(stayDTO.getPhotos()).ifPresent(errorCodes::add);
        checkProperties(stayDTO.getProperties()).ifPresent(errorCodes::add);
        if (!isNull(stayDTO.getAddress())) {
            checkStreet(stayDTO.getAddress().getStreet()).ifPresent(errorCodes::add);
            checkZipCode(stayDTO.getAddress().getZipCode()).ifPresent(errorCodes::add);
            checkCountry(stayDTO.getAddress().getCountry()).ifPresent(errorCodes::add);
            checkCity(stayDTO.getAddress().getCity()).ifPresent(errorCodes::add);
        }
        if(checkId){
        checkId(stayDTO.getId()).ifPresent(errorCodes::add);
        } else{
            if (!isNull(stayDTO.getId())) {
                errorCodes.add(ID_SHOULD_NOT_DEFINED);
            }
        }
        if (!errorCodes.isEmpty()) {
            throw new StayValidationException(errorCodes);
        }
    }


    private Optional<StayErrorCodes> checkId(Long id) {
        if (isNull(id)) {
            return Optional.of(ID_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkProperties(List<String> properties) {
        if (isNull(properties)) {
            return Optional.of(PROPERTIES_NULL);
        }

        for (String property : properties) {
            try {
                StayProperty.valueOf(property);
            } catch (IllegalArgumentException e) {
                throw new StayPropertyNotFoundException(property);
            }
        }

        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkPhotos(List<String> photos) {
        if (isNull(photos)) {
            return Optional.of(PHOTOS_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkCity(String city) {
        if (isNull(city)) {
            return Optional.of(CITY_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkCountry(String country) {
        if (isNull(country)) {
            return Optional.of(COUNTRY_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkZipCode(String zipCode) {
        if (isNull(zipCode)) {
            return Optional.of(ZIPCODE_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkStreet(String street) {
        if (isNull(street)) {
            return Optional.of(STREET_NULL);
        }
        return Optional.empty();

    }

    private Optional<StayErrorCodes> checkPhoneNumber(String phoneNumber) {
        if (isNull(phoneNumber)) {
            return Optional.of(PHONE_NUMBER_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkMinPrice(Long minPrice) {
        if (isNull(minPrice)) {
            return Optional.of(MIN_PRICE_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkCategory(String category) {
        if (isNull(category)) {
            return Optional.of(CATEGORY_NULL);
        }
        try {
            StayCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new StayCategoryNotFoundException(category);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkEmail(String email) {
        if (isNull(email)) {
            return Optional.of(EMAIL_NULL);
        }

        if (!email.equals("") && !EmailValidator.getInstance().isValid(email)) {
            return Optional.of(EMAIL_INVALID);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkAddress(StayDTO.AddressDTO addressDTO) {
        if (isNull(addressDTO)) {
            return Optional.of(ADDRESS_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkUsername(String username) {
        if (isNull(username)) {
            return Optional.of(USERNAME_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkDescription(String description) {
        if (isNull(description)) {
            return Optional.of(DESCRIPTION_NULL);
        }
        return Optional.empty();
    }

    private Optional<StayErrorCodes> checkName(String name) {
        if (isNull(name)) {
            return Optional.of(NAME_NULL);
        }
        return Optional.empty();
    }
}
