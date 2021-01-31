package com.sleepseek.stay;

import com.sleepseek.image.ImageFacade;
import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StayConfiguration {
    public static final Long MAX_PAGE_SIZE = 50L;

    @Bean
    public StayRepositoryAdapter stayRepositoryAdapter(StayRepository stayRepository) {
        return new StayRepositoryAdapterImpl(stayRepository);
    }

    @Bean
    public StayFacade stayFacade(StayRepositoryAdapter stayRepository, ImageFacade imageFacade, UserFacade userFacade) {
        return new StayFacadeImpl(stayRepository, imageFacade, userFacade);
    }
}
