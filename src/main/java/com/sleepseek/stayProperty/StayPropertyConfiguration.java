package com.sleepseek.stayProperty;

import com.sleepseek.property.PropertyDefinitionFacade;
import com.sleepseek.stay.StayFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StayPropertyConfiguration {
    @Bean
    StayPropertyFacade stayPropertyFacade(StayFacade stayFacade, StayPropertyRepository stayPropertyRepository, PropertyDefinitionFacade propertyDefinitionFacade){
        return new StayPropertyFacadeImpl(stayPropertyRepository, stayFacade, propertyDefinitionFacade);
    }

}
