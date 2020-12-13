package com.sleepseek;

import com.sleepseek.image.AmazonProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AmazonProperties.class)
@SpringBootApplication
public class SleepSeekApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleepSeekApplication.class, args);
    }

}
