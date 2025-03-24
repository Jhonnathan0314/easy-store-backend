package com.easy.store.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${easy.store.aws.s3.access.key}")
    private String accessKey;

    @Value("${easy.store.aws.s3.secret.access.key}")
    private String secretAccessKey;

    @Value("${easy.store.aws.s3.easy.store.bucket}")
    private String easyStoreBucketName;

    @Bean
    public S3Client s3Client() {
        return S3Client
                .builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(getCredentialsProvider())
                .build();
    }

    @Bean
    public String easyStoreBucketName() {
        return easyStoreBucketName;
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretAccessKey));
    }

}
