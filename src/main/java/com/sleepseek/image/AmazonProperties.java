package com.sleepseek.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "amazon.properties")
@Getter
@Setter
public class AmazonProperties {
    private String secretKey;
    private String accessKey;
    private String bucketName;
    private String endpointUrl;
    private String region;
}
