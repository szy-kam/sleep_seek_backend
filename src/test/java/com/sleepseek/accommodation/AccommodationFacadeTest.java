package com.sleepseek.accommodation;

import com.sleepseek.accomodation.*;
import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.exception.AccommodationValidationException;
import com.sleepseek.stay.StayFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccommodationFacadeTest {
    private AccommodationFacade accommodationFacade;

    private static final Long VALID_STAY_ID = 1L;
    private static final Long VALID_QUANTITY = 5L;
    private static final Long VALID_SLEEPERS_CAP = 10L;
    private static final Long VALID_PRICE = 10L;

    private List<String> VALID_PROPERTIES = Arrays.asList(AccommodationPropertyDefinition.BATH.getName(), AccommodationPropertyDefinition.COOLING.getName());


    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private AccommodationPropertyRepository propertyRepository;

    @Mock
    private StayFacade stayFacade;

    @BeforeEach
    void initAccommodationFacade() {
        accommodationFacade = new AccommodationConfiguration().accommodationFacade(accommodationRepository, propertyRepository, stayFacade);
    }

    private AccommodationDTO addAccommodation(Long stayId, Long sleepersCapacity, Long quantity, Long price, List<String> properties) {
        return accommodationFacade.addAccommodation(AccommodationDTO.builder()
                .properties(properties)
                .stayId(stayId)
                .quantity(quantity)
                .sleepersCapacity(sleepersCapacity)
                .price(price)
                .build());
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCapacityNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, VALID_SLEEPERS_CAP, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCapacityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCapacityNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCapacityNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, VALID_SLEEPERS_CAP, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, VALID_SLEEPERS_CAP, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayIdNullSleepersCapacityNullQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, null, null, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCapacityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCapacityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, null, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullSleepersCap_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, null, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage()
                ));
    }

    @Test
    void addAccommodation_nullStayId_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(null, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage()
                ));
    }
}
