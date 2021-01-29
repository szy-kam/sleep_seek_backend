package com.sleepseek.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ImageConfiguration {

    @Bean
    public AmazonS3 amazonS3(AmazonProperties amazonProperties) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(amazonProperties.getAccessKey(), amazonProperties.getSecretKey());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(amazonProperties.getRegion())
                .build();
    }

    @Bean
    public ImageFacade imageFacade(ImageRepository imageRepository, AmazonS3 s3client, AmazonProperties amazonProperties, UserFacade userFacade) {
        return new ImageFacadeImpl(new AWSImageStorage(s3client, amazonProperties), imageRepository, userFacade);
    }
}
