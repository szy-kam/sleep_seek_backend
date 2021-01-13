package com.sleepseek.accomodation;

import com.sleepseek.stay.StayFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccommodationConfiguration {

    @Bean
    AccommodationFacade accommodationFacade(AccommodationRepository accommodationRepository, StayFacade stayFacade) {
        return new AccommodationFacadeImpl(accommodationRepository, stayFacade);
    }
}
