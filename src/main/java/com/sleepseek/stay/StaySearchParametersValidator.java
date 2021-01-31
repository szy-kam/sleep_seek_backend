package com.sleepseek.stay;

import com.google.common.collect.Sets;
import com.sleepseek.stay.exception.StaySearchParametersException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.sleepseek.stay.StayConfiguration.MAX_PAGE_SIZE;
import static java.util.Objects.isNull;

class StaySearchParametersValidator {

    private static final Long MAX_PRICE = 1000000L;
    private final List<String> SUPPORTED_ORDER_BY = Arrays.asList("name", "avgRate", "city", "createdAt");

    void validateSearchParameters(StaySearchParameters searchParameters) {
        Set<StaySearchParametersErrorCodes> errorCodes = Sets.newHashSet();
        checkPrice(searchParameters.getPriceFrom()).ifPresent(errorCodes::add);
        checkPrice(searchParameters.getPriceTo()).ifPresent(errorCodes::add);
        checkCategorySearch(searchParameters.getCategory()).ifPresent(errorCodes::add);
        checkPropertiesSearch(searchParameters.getProperty()).ifPresent(errorCodes::add);
        checkDate(searchParameters.getDateTo()).ifPresent(errorCodes::add);
        checkDate(searchParameters.getDateFrom()).ifPresent(errorCodes::add);
        checkPageNumber(searchParameters.getPageNumber()).ifPresent(errorCodes::add);
        checkPageSize(searchParameters.getPageSize()).ifPresent(errorCodes::add);
        checkOrderBy(searchParameters.getOrderBy()).ifPresent(errorCodes::add);
        checkOrder(searchParameters.getOrder()).ifPresent(errorCodes::add);
        if (!errorCodes.isEmpty()) {
            throw new StaySearchParametersException(errorCodes);
        }
    }

    private Optional<StaySearchParametersErrorCodes> checkOrder(String order) {
        if (!isNull(order)) {
            if (!order.equalsIgnoreCase("ASC") && !order.equalsIgnoreCase("DESC")) {
                return Optional.of(StaySearchParametersErrorCodes.ORDER_INVALID);
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkOrderBy(String orderBy) {
        if (!isNull(orderBy)) {
            if (!SUPPORTED_ORDER_BY.contains(orderBy)) {
                return Optional.of(StaySearchParametersErrorCodes.ORDER_BY_INVALID);
            }
        }

        return Optional.empty();
    }


    private Optional<StaySearchParametersErrorCodes> checkDate(String dateFrom) {
        if (!isNull(dateFrom)) {
            try {
                LocalDate.parse(dateFrom);
            } catch (DateTimeParseException e) {
                return Optional.of(StaySearchParametersErrorCodes.DATES_OUT_OF_BOUNDS);
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPageSize(Integer pageSize) {
        if (isNull(pageSize) || pageSize < 0 || pageSize > MAX_PAGE_SIZE) {
            return Optional.of(StaySearchParametersErrorCodes.WRONG_PAGE_CONSTRAINTS);
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPageNumber(Integer pageNumber) {
        if (isNull(pageNumber) || pageNumber < 0) {
            return Optional.of(StaySearchParametersErrorCodes.WRONG_PAGE_CONSTRAINTS);
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkCategorySearch(String category) {
        if (!isNull(category)) {
            try {
                StayCategory.valueOf(category);
            } catch (IllegalArgumentException e) {
                return Optional.of(StaySearchParametersErrorCodes.CATEGORY_NOT_FOUND);
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPropertiesSearch(List<String> properties) {
        if (!isNull(properties)) {
            for (String property : properties) {
                try {
                    StayProperty.valueOf(property);
                } catch (IllegalArgumentException e) {
                    return Optional.of(StaySearchParametersErrorCodes.PROPERTY_NOT_FOUND);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<StaySearchParametersErrorCodes> checkPrice(Long price) {
        if (!isNull(price)) {
            if (price < 0 || price > MAX_PRICE) {
                return Optional.of(StaySearchParametersErrorCodes.PRICE_OUT_OF_BOUNDS);
            }
        }


        return Optional.empty();
    }
}
