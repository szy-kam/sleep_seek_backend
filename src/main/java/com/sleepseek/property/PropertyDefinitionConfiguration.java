package com.sleepseek.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PropertyDefinitionConfiguration {

    @Bean
    PropertyDefinitionFacade propertyDefinitionFacade(PropertyDefinitionRepository propertyDefinitionRepository){
        return new PropertyDefinitionFacadeImpl(propertyDefinitionRepository);
    }
}
