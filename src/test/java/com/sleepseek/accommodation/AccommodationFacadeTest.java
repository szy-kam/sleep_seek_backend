package com.sleepseek.accommodation;

import com.sleepseek.accomodation.*;
import com.sleepseek.accomodation.DTO.AccommodationDTO;
import com.sleepseek.accomodation.exception.AccommodationNotFoundException;
import com.sleepseek.accomodation.exception.AccommodationValidationException;
import com.sleepseek.stay.StayFacade;
import com.sleepseek.stay.exception.StayNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccommodationFacadeTest {
    private AccommodationFacade accommodationFacade;

    private static final Long VALID_ACCOMMODATION_ID = 9L;
    private static final Long NOT_EXISTING_ACCOMMODATION_ID = 12L;
    private static final Long VALID_STAY_ID = 1L;
    private static final Long NOT_EXISTING_STAY_ID = 11L;
    private static final Long VALID_QUANTITY = 5L;
    private static final Long INVALID_QUANTITY = -1L;
    private static final Long VALID_SLEEPERS_CAP = 10L;
    private static final Long INVALID_SLEEPERS_CAP = -1L;
    private static final Long VALID_PRICE = 10L;
    private static final Long INVALID_PRICE = -1L;


    private final List<String> VALID_PROPERTIES = Arrays.asList(AccommodationPropertyDefinition.BATH.getName(), AccommodationPropertyDefinition.COOLING.getName());
    private final List<String> INVALID_PROPERTIES = Arrays.asList("bar", "foo");


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

    private AccommodationDTO updateAccommodation(Long accommodationId, Long stayId, Long sleepersCapacity, Long quantity, Long price, List<String> properties) {
        return accommodationFacade.updateAccommodation(AccommodationDTO.builder()
                .price(price)
                .id(accommodationId)
                .sleepersCapacity(sleepersCapacity)
                .quantity(quantity)
                .properties(properties)
                .stayId(stayId)
                .build());
    }

    @Test
    void deleteAccommodation_accommodationNotExist_throwsExceptionAccommodationNotFound(){
        Mockito.when(accommodationRepository.existsById(NOT_EXISTING_ACCOMMODATION_ID)).thenReturn(false);
        assertThrows(AccommodationNotFoundException.class, () -> accommodationFacade.deleteAccommodation(NOT_EXISTING_ACCOMMODATION_ID));
    }

    @Test
    void addAccommodation_stayNotExist_throwsExceptionStayNotFound() {
        Mockito.when(stayFacade.stayExists(NOT_EXISTING_STAY_ID)).thenReturn(false);
        assertThrows(StayNotFoundException.class, () -> addAccommodation(NOT_EXISTING_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
    }

    @Test
    void addAccommodation_invalidPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, INVALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PRICE_OUT_OF_BOUNDS.getMessage()
                ));
    }

    @Test
    void addAccommodation_invalidQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, VALID_SLEEPERS_CAP, INVALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_BOUNDARIES.getMessage()
                ));
    }

    @Test
    void addAccommodation_invalidSleepersCap_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> addAccommodation(VALID_STAY_ID, INVALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_BOUNDARIES.getMessage()
                ));
    }


    @Test
    void updateAccommodation_invalidPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, INVALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PRICE_OUT_OF_BOUNDS.getMessage()
                ));
    }

    @Test
    void updateAccommodation_invalidQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, INVALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_BOUNDARIES.getMessage()
                ));
    }

    @Test
    void updateAccommodation_invalidSleepersCap_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, INVALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_BOUNDARIES.getMessage()
                ));
    }

    @Test
    void updateAccommodation_accommodationNotFound_throwsExceptionAccommodationNotFound() {
        Mockito.when(accommodationRepository.existsById(NOT_EXISTING_ACCOMMODATION_ID)).thenReturn(false);
        Mockito.when(stayFacade.stayExists(VALID_STAY_ID)).thenReturn(true);
        assertThrows(AccommodationNotFoundException.class, () -> updateAccommodation(NOT_EXISTING_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));

    }

    @Test
    void updateAccommodation_stayNotFound_throwsExceptionAccommodationNotFound() {
        Mockito.when(stayFacade.stayExists(NOT_EXISTING_STAY_ID)).thenReturn(false);
        assertThrows(StayNotFoundException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, NOT_EXISTING_STAY_ID, VALID_ACCOMMODATION_ID, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));

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

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, null, null, null));
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
    void updateAccommodation_nullSleepersCapacityNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, VALID_SLEEPERS_CAP, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullSleepersCapacityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullSleepersCapacityNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullSleepersCapacityNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, VALID_SLEEPERS_CAP, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, VALID_SLEEPERS_CAP, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullStayIdNullSleepersCapacityNullQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, null, null, null, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullSleepersCapacityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullSleepersCapacityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullSleepersCap_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(VALID_ACCOMMODATION_ID, VALID_STAY_ID, null, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCapacityNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, VALID_SLEEPERS_CAP, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullQuantityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCapacityNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCapacityNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCapacityNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, VALID_SLEEPERS_CAP, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, VALID_SLEEPERS_CAP, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullStayIdNullSleepersCapacityNullQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, null, null, null, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.STAY_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullPriceNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullQuantityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullQuantityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCapacityNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCapacityNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullProperties_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, null));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.PROPERTIES_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullPrice_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, null, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.PRICE_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullQuantity_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, null, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.QUANTITY_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationIdNullSleepersCap_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, null, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage(),
                        AccommodationErrorCodes.SLEEPERS_CAP_NULL.getMessage()
                ));
    }

    @Test
    void updateAccommodation_nullAccommodationId_throwsExceptionWithErrorCodes() {
        var exception = assertThrows(AccommodationValidationException.class, () -> updateAccommodation(null, VALID_STAY_ID, VALID_SLEEPERS_CAP, VALID_QUANTITY, VALID_PRICE, VALID_PROPERTIES));
        assertAll(
                () -> assertThat(exception.getMessage()).contains(
                        AccommodationErrorCodes.ID_NULL.getMessage()
                ));
    }

}
