package com.easy.store.backend.context.s3.service;

import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.model.S3ObjectContent;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024;

    private final S3Client s3Client;
    private final String bucketName;

    /**
     * Devuelve el contenido crudo del objeto (bytes + ETag, sin base64): lo usan los endpoints
     * que sirven la imagen como recurso HTTP propio (Content-Type + Cache-Control + ETag), no el
     * flujo de subida (que sigue viajando en base64 dentro del JSON de creación/actualización).
     * Cualquier fallo se resuelve como Optional.empty() para que el endpoint de imagen responda
     * 404 en vez de tumbar la página con un 500 (una imagen rota no debe romper el listado
     * completo) -- pero siempre se loggea, distinguiendo "no existe" (esperable) de un error real.
     */
    public Optional<S3ObjectContent> getObjectContent(Long accountId, String context, String objectName) {
        String key = "account/" + accountId + "/" + context + "/" + objectName;

        log.info("ACCION GETOBJECT -> Iniciando búsqueda con key: {}", key);

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try (ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(request)) {
            byte[] bytes = responseInputStream.readAllBytes();
            String etag = responseInputStream.response().eTag();

            log.info("ACCION GETOBJECT -> Retornando archivo con key: {}", key);
            return Optional.of(S3ObjectContent.builder()
                    .bytes(bytes)
                    .contentType(resolveContentType(objectName))
                    .etag(etag)
                    .build());
        } catch (NoSuchKeyException e) {
            log.warn("ACCION GETOBJECT -> No existe el archivo con key: {}", key);
            return Optional.empty();
        } catch (IOException e) {
            log.error("ACCION GETOBJECT -> Error al leer el archivo con key: {}", key, e);
            return Optional.empty();
        } catch (S3Exception e) {
            log.error("ACCION GETOBJECT -> Error de S3 al leer el archivo con key: {}", key, e);
            return Optional.empty();
        }
    }

    public boolean putObject(S3File objectContent) throws FileException {
        if (objectContent.getContent() == null || objectContent.getContent().isBlank()) {
            throw new FileException(ErrorMessages.EMPTY_FILE);
        }

        byte[] fileContent;
        try {
            fileContent = Base64.getDecoder().decode(objectContent.getContent());
        } catch (IllegalArgumentException e) {
            throw new FileException(ErrorMessages.INVALID_FILE);
        }

        if (fileContent.length > MAX_FILE_SIZE_BYTES) {
            throw new FileException(ErrorMessages.FILE_TOO_LARGE);
        }

        String key = "account/" + objectContent.getAccountId() + "/" + objectContent.getContext() + "/" + objectContent.getName();

        log.info("ACCION PUTOBJECT KEY -> {}", key);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            log.info("ACCION PUTOBJECT INICIA CARGUE DE ARCHIVO");
            PutObjectResponse response = s3Client.putObject(
                    request,
                    RequestBody.fromBytes(fileContent)
            );
            log.info("ACCION PUTOBJECT FINALIZA CARGUE EXITOSO");
            return response != null && response.eTag() != null && !response.eTag().isEmpty();
        } catch (S3Exception e) {
            log.error("ACCION PUTOBJECT FINALIZA CARGUE CON ERROR: {}", e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : e.getMessage(), e);
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

    private String resolveContentType(String fileName) {
        return switch (getExtension(fileName).toLowerCase()) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }

}
