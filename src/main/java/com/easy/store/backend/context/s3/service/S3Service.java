package com.easy.store.backend.context.s3.service;

import com.easy.store.backend.context.s3.model.S3File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Base64;


@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3File getObject(Long accountId, String context, String objectName) throws IOException {
        String key = "account/" + accountId + "/" + context + "/" + objectName;

        log.info("ACCION GETOBJECT -> Iniciando búsqueda con key: {}", key);

        log.info("ACCION GETOBJECT -> Buscando archivo");
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(request);

        String extension = getExtension(objectName);
        byte[] bytes = responseInputStream.readAllBytes();
        String base64Content = Base64.getEncoder().encodeToString(bytes);

        log.info("ACCION GETOBJECT -> Retornando archivo");
        return S3File.builder()
                .extension(extension)
                .name(objectName)
                .content(base64Content)
                .build();
    }

    public boolean putObject(S3File objectContent) {

        String key = "account/" + objectContent.getAccountId() + "/" + objectContent.getContext() + "/" + objectContent.getName();

        log.info("ACCION PUTOBJECT KEY -> {}", key);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        byte[] fileContent = Base64.getDecoder().decode(objectContent.getContent());

        try {
            log.info("ACCION PUTOBJECT INICIA CARGUE DE ARCHIVO");
            PutObjectResponse response = s3Client.putObject(
                    request,
                    RequestBody.fromBytes(fileContent)
            );
            log.info("ACCION PUTOBJECT FINALIZA CARGUE EXITOSO");
            return response != null && response.eTag() != null && !response.eTag().isEmpty();
        } catch (S3Exception e) {
            log.info("ACCION PUTOBJECT FINALIZA CARGUE CON ERROR" + e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    public boolean deleteObject(Long accountId, String context, String objectName) {
        String key = "account/" + accountId + "/" + context + "/" + objectName;

        log.info("ACCION DELETEOBJECT -> Iniciando eliminado con key: {}", key);

        log.info("ACCION DELETEOBJECT -> Eliminando archivo");
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(request);
        log.info("ACCION DELETEOBJECT -> Archivo eliminado");
        return true;
    }

    private String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex + 1) : "unknown";
    }

}
