package com.sophie.store.backend.context.purchase_has_product.presentation.controller;

import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.sophie.store.backend.context.purchase_has_product.application.usecase.*;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductAddMapper;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductResponseMapper;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-has-product")
@RequiredArgsConstructor
public class PurchaseHasProductController {

    private final FindByIdPurchaseHasProductUseCase findByIdPurchaseHasProductUseCase;
    private final FindAllByPurchaseIdPurchaseHasProductUseCase findAllByPurchaseIdPurchaseHasProductUseCase;
    private final FindAllByProductIdPurchaseHasProductUseCase findAllByProductIdPurchaseHasProductUseCase;
    private final AddPurchaseHasProductUseCase addPurchaseHasProductUseCase;
    private final RemoveByIdPurchaseHasProductUseCase removeByIdPurchaseHasProductUseCase;

    private final PurchaseHasProductAddMapper purchaseHasProductAddMapper = new PurchaseHasProductAddMapper();
    private final PurchaseHasProductResponseMapper purchaseHasProductResponseMapper = new PurchaseHasProductResponseMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        try {
            PurchaseHasProductResponseDTO purchaseHasProduct = purchaseHasProductResponseMapper.modelToDto(findByIdPurchaseHasProductUseCase.findById(id));
            response.setData(purchaseHasProduct);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<ApiResponse<List<PurchaseHasProductResponseDTO>>> findAllByPurhaseId(@PathVariable Long purchaseId) {
        ApiResponse<List<PurchaseHasProductResponseDTO>> response = new ApiResponse<>();
        try {
            List<PurchaseHasProductResponseDTO> purchaseHasProducts = purchaseHasProductResponseMapper.modelsToDtos(findAllByPurchaseIdPurchaseHasProductUseCase.findAllByPurchaseId(purchaseId));
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
            List<PurchaseHasProductResponseDTO> purchaseHasProducts = purchaseHasProductResponseMapper.modelsToDtos(findAllByProductIdPurchaseHasProductUseCase.findAllByProductId(productId));
            response.setData(purchaseHasProducts);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> create(@RequestBody PurchaseHasProductAddDTO purchaseHasProduct,
                                                                             @RequestHeader("Purchase-Id") Long purchaseId,
                                                                             @RequestHeader("Product-Id") Long productId) {
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        try {
            response.setData(purchaseHasProductResponseMapper.modelToDto(addPurchaseHasProductUseCase.add(purchaseHasProductAddMapper.dtoToModel(purchaseHasProduct), purchaseId, productId)));
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException | NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            removeByIdPurchaseHasProductUseCase.removeById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
