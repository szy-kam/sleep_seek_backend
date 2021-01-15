package com.sleepseek.stay;

import com.sleepseek.image.ImageRepository;
import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StayConfiguration {

    @Bean
    public StayFacade stayFacade(StayRepository stayRepository, ImageRepository imageRepository, UserFacade userFacade) {
        return new StayFacadeImpl(stayRepository, imageRepository, userFacade);
    }
}
