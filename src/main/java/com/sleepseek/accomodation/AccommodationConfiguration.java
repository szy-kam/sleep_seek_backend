package com.sleepseek.accomodation;

import com.sleepseek.stay.StayFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccommodationConfiguration {

    @Bean
    AccommodationRepositoryAdapter accommodationRepositoryAdapter(AccommodationRepository repository) {
        return new AccommodationRepositoryAdapterImpl(repository);
    }

    @Bean
    AccommodationFacade accommodationFacade(AccommodationRepositoryAdapter accommodationRepository, AccommodationTemplateRepository accommodationTemplaterepository, StayFacade stayFacade) {
        return new AccommodationFacadeImpl(accommodationRepository, accommodationTemplaterepository, stayFacade);
    }
}
