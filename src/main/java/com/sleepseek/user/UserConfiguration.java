package com.sleepseek.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfiguration {
    @Bean
    UserFacade userFacade(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new UserFacadeImpl(userRepository, passwordEncoder);
    }
}
