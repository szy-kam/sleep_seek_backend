package com.sleepseek.stay;

import com.sleepseek.image.ImageRepository;
import com.sleepseek.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StayConfiguration {

    @Bean
    public StayFacade stayFacade(StayRepository stayRepository, ImageRepository imageRepository, UserRepository userRepository) {
        return new StayFacadeImpl(stayRepository, imageRepository, userRepository);
    }
}
