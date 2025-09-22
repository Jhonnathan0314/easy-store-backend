package com.easy.store.backend.context.s3.controller;

import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.exceptions.*;
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

    @GetMapping("/get/accountId/{accountId}/context/{context}/objectName/{objectName}")
    public ResponseEntity<ApiResponse<S3File>> getObjectName(
            @PathVariable("accountId") Long accountId,
            @PathVariable String context,
            @PathVariable String objectName) throws FileException {
        ApiResponse<S3File> response = new ApiResponse<>();
        S3File file = s3Service.getObject(accountId, context, objectName);
        response.setData(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/put")
    public ResponseEntity<ApiResponse<Boolean>> putObjectName(@RequestBody S3File file) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        boolean isCreated = s3Service.putObject(file);
        response.setData(isCreated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/accountId/{accountId}/context/{context}/objectName/{objectName}")
    public ResponseEntity<ApiResponse<Boolean>> deleteObjectName(
            @PathVariable("accountId") Long accountId,
            @PathVariable String context,
            @PathVariable String objectName) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        boolean isDeleted = s3Service.deleteObject(accountId, context, objectName);
        response.setData(isDeleted);
        return ResponseEntity.ok(response);
    }

}
