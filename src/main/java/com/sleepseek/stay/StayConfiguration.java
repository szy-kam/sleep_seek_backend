package com.sleepseek.stay;

import com.sleepseek.image.ImageFacade;
import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StayConfiguration {
    public static Long MAX_PAGE_SIZE = 50L;

    @Bean
    public StayFacade stayFacade(StayRepository stayRepository, ImageFacade imageFacade, UserFacade userFacade) {
        return new StayFacadeImpl(stayRepository, imageFacade, userFacade);
    }
}
