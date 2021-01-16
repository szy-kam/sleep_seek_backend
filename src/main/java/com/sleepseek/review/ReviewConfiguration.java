package com.sleepseek.review;

import com.sleepseek.stay.StayFacade;
import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewConfiguration {

    @Bean
    ReviewFacade reviewFacade(StayFacade stayFacade, ReviewRepository reviewRepository, UserFacade userFacade) {
        return new ReviewFacadeImpl(stayFacade, reviewRepository, userFacade);
    }
}
