package com.easy.store.backend.context.s3.service;

import com.easy.store.backend.context.s3.model.S3File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3File getObject(Long accountId, String context, String objectName) throws IOException {
        String key = "account/" + accountId + "/" + context + "/" + objectName;

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(request);

        String extension = getExtension(objectName);
        byte[] bytes = responseInputStream.readAllBytes();
        String base64Content = Base64.getEncoder().encodeToString(bytes);

        return S3File.builder()
                .extension(extension)
                .name(objectName)
                .content(base64Content)
                .build();
    }

    public boolean putObject(Long accountId, String context, String objectName, S3File objectContent) {
        String key = "account/" + accountId + "/" + context + "/" + objectName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        byte[] fileContent = Base64.getDecoder().decode(objectContent.getContent());

        PutObjectResponse response = s3Client.putObject(
                request,
                RequestBody.fromBytes(fileContent)
        );

        return !response.eTag().isEmpty();
    }

    public boolean deleteObject(Long accountId, String context, String objectName) {
        String key = "account/" + accountId + "/" + context + "/" + objectName;
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
        return true;
    }

    private String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex + 1) : "unknown";
    }

}
