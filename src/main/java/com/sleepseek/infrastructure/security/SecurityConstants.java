package com.sleepseek.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.security")
@Getter
@Setter
public class SecurityConstants {
    private String key;
    private long expirationTime;
    private String tokenPrefix;
    private String headerString;
}