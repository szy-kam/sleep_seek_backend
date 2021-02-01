package com.sleepseek.accomodation;


import com.google.common.collect.Sets;
import com.sleepseek.accomodation.DTO.AccommodationTemplateDTO;
import com.sleepseek.accomodation.exception.AccommodationPropertyNotFound;
import com.sleepseek.accomodation.exception.AccommodationValidationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sleepseek.accomodation.AccommodationErrorCodes.*;
import static java.util.Objects.isNull;

public class AccommodationTemplateValidator {
    public void validate(AccommodationTemplateDTO object, boolean shouldCheckId, boolean shouldCheckStayId) {
        Set<AccommodationErrorCodes> errors = Sets.newHashSet();
        if (shouldCheckId) {
            checkId(object.getId()).ifPresent(errors::add);
        }
        if (shouldCheckStayId) {
            checkStay(object.getStayId()).ifPresent(errors::add);
        }
        checkQuantity(object.getQuantity()).ifPresent(errors::add);
        checkPrice(object.getPrice()).ifPresent(errors::add);
        checkSleepersCapacity(object.getSleepersCapacity()).ifPresent(errors::add);
        checkProperties(object.getProperties()).ifPresent(errors::add);
        checkPrefix(object.getPrefix()).ifPresent(errors::add);
        if (!errors.isEmpty()) {
            throw new AccommodationValidationException(errors);
        }
    }

    private Optional<AccommodationErrorCodes> checkPrefix(String prefix) {
        if (isNull(prefix)) {
            return Optional.of(PREFIX_NULL);
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkProperties(List<String> properties) {
        if (isNull(properties)) {
            return Optional.of(PROPERTIES_NULL);
        }
        for (String property : properties) {
            try {
                AccommodationProperty.valueOf(property);
            } catch (IllegalArgumentException exception) {
                throw new AccommodationPropertyNotFound(property);
            }
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkId(Long id) {
        if (isNull(id)) {
            return Optional.of(ID_NULL);
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkSleepersCapacity(Long sleepersCapacity) {
        if (isNull(sleepersCapacity)) {
            return Optional.of(SLEEPERS_CAP_NULL);
        }
        if (sleepersCapacity < 0) {
            return Optional.of(SLEEPERS_CAP_BOUNDARIES);
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkPrice(Long price) {
        if (isNull(price)) {
            return Optional.of(PRICE_NULL);
        }
        if (price < 0L) {
            return Optional.of(PRICE_OUT_OF_BOUNDS);
        }
        return Optional.empty();
    }

    private Optional<AccommodationErrorCodes> checkQuantity(Long quantity) {
        if (isNull(quantity)) {
            return Optional.of(QUANTITY_NULL);
        }
        if (quantity < 0) {
            return Optional.of(QUANTITY_BOUNDARIES);
        }
        return Optional.empty();

    }

    private Optional<AccommodationErrorCodes> checkStay(Long stayId) {
        if (isNull(stayId)) {
            return Optional.of(STAY_NULL);
        }
        return Optional.empty();
    }

}
