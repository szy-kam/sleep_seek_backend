package com.sleepseek.accommodationProperty;

import com.sleepseek.accomodation.AccommodationFacade;
import com.sleepseek.property.PropertyDefinitionFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccommodationPropertyConfiguration {

    @Bean
    AccommodationPropertyFacade accommodationPropertyFacade(AccommodationPropertyRepository accommodationPropertyRepository,
                                                            PropertyDefinitionFacade propertyDefinitionFacade,
                                                            AccommodationFacade accommodationFacade) {
        return new AccommodationPropertyFacadeImpl(accommodationPropertyRepository, propertyDefinitionFacade, accommodationFacade);
    }
}
