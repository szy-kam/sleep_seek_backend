package com.sleepseek.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
class AWSImageStorage implements ImageStorage {
    private final AmazonS3 s3client;
    private final AmazonProperties amazonProperties;


    AWSImageStorage(AmazonS3 s3client, AmazonProperties amazonProperties) {
        this.s3client = s3client;
        this.amazonProperties = amazonProperties;
    }


    @Override
    public String uploadFile(File file, String fileName) {
        s3client.putObject(new PutObjectRequest(amazonProperties.getBucketName(), fileName, file));
        return amazonProperties.getEndpointUrl() + "/" + fileName;
    }
}
