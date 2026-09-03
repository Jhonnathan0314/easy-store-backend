package com.easy.store.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${easy.store.aws.s3.access.key}")
    private String accessKey;

    @Value("${easy.store.aws.s3.secret.access.key}")
    private String secretAccessKey;

    @Value("${easy.store.aws.s3.easy.store.bucket}")
    private String easyStoreBucketName;

    // Opcional: solo se usa en entornos locales/desarrollo para apuntar a un
    // emulador de S3 (p. ej. LocalStack). En producción se deja vacío y el
    // SDK usa los endpoints reales de AWS.
    @Value("${easy.store.aws.s3.endpoint:}")
    private String endpointOverride;

    @Bean
    public S3Client s3Client() {
        S3ClientBuilder builder = S3Client
                .builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(getCredentialsProvider());

        if (StringUtils.hasText(endpointOverride)) {
            builder = builder
                    .endpointOverride(URI.create(endpointOverride))
                    // LocalStack (y la mayoría de emuladores de S3) requieren
                    // path-style en vez de virtual-hosted-style.
                    .forcePathStyle(true);
        }

        return builder.build();
    }

    @Bean
    public String easyStoreBucketName() {
        return easyStoreBucketName;
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretAccessKey));
    }

}
