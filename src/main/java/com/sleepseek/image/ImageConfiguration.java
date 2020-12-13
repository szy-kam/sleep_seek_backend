package com.sleepseek.image;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ImageConfiguration {
    @Bean
    public ImageFacade imageFacade(){
        return new ImageFacadeImpl();
    }
}
