package com.sleepseek.accomodation;

import com.sleepseek.stay.StayFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccommodationConfiguration {

    @Bean
    public AccommodationFacade accommodationFacade(AccommodationRepository accommodationRepository, StayFacade stayFacade) {
        return new AccommodationFacadeImpl(accommodationRepository, stayFacade);
    }
}
