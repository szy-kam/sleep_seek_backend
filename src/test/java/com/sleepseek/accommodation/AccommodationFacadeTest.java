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

}
