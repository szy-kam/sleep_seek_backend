package com.sleepseek.stay;

import com.sleepseek.image.ImageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StayConfiguration {

    @Bean
    public StayFacade stayFacade(StayRepository stayRepository, ImageRepository imageRepository) {
        return new StayFacadeImpl(stayRepository, imageRepository);
    }
}
