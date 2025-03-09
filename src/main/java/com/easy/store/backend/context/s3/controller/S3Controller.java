package com.easy.store.backend.context.s3.controller;

import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
@CrossOrigin("*")
public class S3Controller {

    private final S3Service s3Service;

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping("/get/accountId/{accountId}/context/{context}/objectName/{objectName}")
    public ResponseEntity<ApiResponse<S3File>> getObjectName(
            @PathVariable("accountId") Long accountId,
            @PathVariable String context,
            @PathVariable String objectName) {
        ApiResponse<S3File> response = new ApiResponse<>();
        try {
            S3File file = s3Service.getObject(accountId, context, objectName);
            response.setData(file);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping("/put/accountId/{accountId}/context/{context}/objectName/{objectName}")
    public ResponseEntity<ApiResponse<Boolean>> putObjectName(
            @PathVariable("accountId") Long accountId,
            @PathVariable String context,
            @PathVariable String objectName,
            @RequestBody S3File file) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            boolean isCreated = s3Service.putObject(accountId, context, objectName, file);
            response.setData(isCreated);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/delete/accountId/{accountId}/context/{context}/objectName/{objectName}")
    public ResponseEntity<ApiResponse<Boolean>> deleteObjectName(
            @PathVariable("accountId") Long accountId,
            @PathVariable String context,
            @PathVariable String objectName) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            boolean isDeleted = s3Service.deleteObject(accountId, context, objectName);
            response.setData(isDeleted);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }
}
