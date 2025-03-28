package com.easy.store.backend.context.s3.controller;

import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductResponseMapper;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.s3.service.S3ProductService;
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
    private final S3ProductService s3ProductService;

    private final ProductResponseMapper responseMapper = new ProductResponseMapper();

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

    @PostMapping("/put")
    public ResponseEntity<ApiResponse<Boolean>> putObjectName(@RequestBody S3File file) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            boolean isCreated = s3Service.putObject(file);
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

    @PutMapping("/product/put/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> putProductFile(@PathVariable Long productId, @RequestBody S3File s3File) {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        try {
            Product product = s3ProductService.addFile(s3File, productId);
            response.setData(responseMapper.modelToDto(product));
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping("/product/delete/{productId}/account/{accountId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> putProductFile(
            @PathVariable Long productId,
            @PathVariable Long accountId,
            @RequestBody S3File s3File) {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        try {
            Product product = s3ProductService.deleteFile(s3File, productId, accountId);
            response.setData(responseMapper.modelToDto(product));
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
