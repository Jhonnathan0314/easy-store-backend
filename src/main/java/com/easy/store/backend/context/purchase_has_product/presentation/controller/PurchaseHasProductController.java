package com.easy.store.backend.context.purchase_has_product.presentation.controller;

import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductUpdateDTO;
import com.easy.store.backend.context.purchase_has_product.application.usecase.*;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductAddMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductResponseMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-has-product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PurchaseHasProductController {

    private final FindByPurchaseIdAndProductIdPurchaseHasProductUseCase findByPurchaseIdAndProductIdPurchaseHasProductUseCase;
    private final FindByPurchaseIdPurchaseHasProductUseCase findByPurchaseIdPurchaseHasProductUseCase;
    private final FindByProductIdPurchaseHasProductUseCase findByProductIdPurchaseHasProductUseCase;
    private final AddPurchaseHasProductUseCase addPurchaseHasProductUseCase;
    private final AddAllPurchaseHasProductUseCase addAllPurchaseHasProductUseCase;
    private final UpdatePurchaseHasProductUseCase updatePurchaseHasProductUseCase;
    private final RemoveByIdPurchaseHasProductUseCase removeByIdPurchaseHasProductUseCase;
    private final RemoveAllPurchaseHasProductUseCase removeAllPurchaseHasProductUseCase;

    private final PurchaseHasProductAddMapper purchaseHasProductAddMapper = new PurchaseHasProductAddMapper();
    private final PurchaseHasProductUpdateMapper purchaseHasProductUpdateMapper = new PurchaseHasProductUpdateMapper();
    private final PurchaseHasProductResponseMapper purchaseHasProductResponseMapper = new PurchaseHasProductResponseMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping("/purchase/{purchaseId}/product/{productId}")
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> findByPurchaseIdAndProductId(@PathVariable Long purchaseId, @PathVariable Long productId) {
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        try {
            PurchaseHasProductId id = PurchaseHasProductId.builder()
                    .purchaseId(purchaseId)
                    .productId(productId)
                    .build();
            PurchaseHasProductResponseDTO purchaseHasProduct = purchaseHasProductResponseMapper.modelToDto(findByPurchaseIdAndProductIdPurchaseHasProductUseCase.findByPurchaseIdAndProductId(id));
            response.setData(purchaseHasProduct);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<ApiResponse<List<PurchaseHasProductResponseDTO>>> findAllByPurchaseId(@PathVariable Long purchaseId) {
        ApiResponse<List<PurchaseHasProductResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseHasProductResponseDTO> purchaseHasProducts = purchaseHasProductResponseMapper.modelsToDtos(findByPurchaseIdPurchaseHasProductUseCase.findByPurchaseId(purchaseId));
            response.setData(purchaseHasProducts);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<PurchaseHasProductResponseDTO>>> findAllByProductIdId(@PathVariable Long productId) {
        ApiResponse<List<PurchaseHasProductResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseHasProductResponseDTO> purchaseHasProducts = purchaseHasProductResponseMapper.modelsToDtos(findByProductIdPurchaseHasProductUseCase.findByProductId(productId));
            response.setData(purchaseHasProducts);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> add(@RequestBody PurchaseHasProductAddDTO purchaseHasProduct) {
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        try {
            response.setData(purchaseHasProductResponseMapper.modelToDto(addPurchaseHasProductUseCase.add(purchaseHasProductAddMapper.dtoToModel(purchaseHasProduct))));
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | NoResultsException | NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PatchMapping("/add/all")
    public ResponseEntity<ApiResponse<List<PurchaseHasProductResponseDTO>>> addAll(@RequestBody List<PurchaseHasProductAddDTO> purchaseHasProducts) {
        ApiResponse<List<PurchaseHasProductResponseDTO>> response = new ApiResponse<>();
        try {
            response.setData(purchaseHasProductResponseMapper.modelsToDtos(addAllPurchaseHasProductUseCase.addAll(purchaseHasProductAddMapper.dtosToModels(purchaseHasProducts))));
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> update(@RequestBody PurchaseHasProductUpdateDTO purchaseHasProduct) {
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        try {
            response.setData(purchaseHasProductResponseMapper.modelToDto(updatePurchaseHasProductUseCase.update(purchaseHasProductUpdateMapper.dtoToModel(purchaseHasProduct))));
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | NoResultsException | NoChangesException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/purchase/{purchaseId}/product/{productId}")
    public ResponseEntity<ApiResponse<Object>> deleteByPurchaseIdAndProductId(@PathVariable Long purchaseId, @PathVariable Long productId) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            PurchaseHasProductId id = PurchaseHasProductId.builder()
                    .purchaseId(purchaseId)
                    .productId(productId)
                    .build();
            removeByIdPurchaseHasProductUseCase.removeByPurchaseIdAndProductId(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PatchMapping("/remove/all")
    public ResponseEntity<ApiResponse<Object>> deleteByPurchaseIdAndProductId(@RequestBody List<PurchaseHasProductId> ids) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            removeAllPurchaseHasProductUseCase.removeAll(ids);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NoIdReceivedException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
