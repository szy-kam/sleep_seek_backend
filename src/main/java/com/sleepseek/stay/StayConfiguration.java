package com.sleepseek.stay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StayConfiguration {

    @Bean
    public StayFacade stayFacade(StayRepository stayRepository) {
        return new StayFacadeImpl(stayRepository);
    }
}
