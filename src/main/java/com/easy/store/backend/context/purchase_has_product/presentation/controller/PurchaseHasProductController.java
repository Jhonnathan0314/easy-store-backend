package com.easy.store.backend.context.purchase_has_product.presentation.controller;

import com.easy.store.backend.context.purchase.application.service.PurchaseAuthorizationService;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductUpdateDTO;
import com.easy.store.backend.context.purchase_has_product.application.usecase.*;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductAddMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductResponseMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductUpdateMapper;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/purchase-has-product")
@RequiredArgsConstructor
public class PurchaseHasProductController {

    private final AddPurchaseHasProductUseCase addPurchaseHasProductUseCase;
    private final AddAllPurchaseHasProductUseCase addAllPurchaseHasProductUseCase;
    private final UpdatePurchaseHasProductUseCase updatePurchaseHasProductUseCase;
    private final RemoveByIdPurchaseHasProductUseCase removeByIdPurchaseHasProductUseCase;
    private final RemoveAllPurchaseHasProductUseCase removeAllPurchaseHasProductUseCase;
    private final PurchaseAuthorizationService purchaseAuthorizationService;

    private final PurchaseHasProductAddMapper purchaseHasProductAddMapper = new PurchaseHasProductAddMapper();
    private final PurchaseHasProductUpdateMapper purchaseHasProductUpdateMapper = new PurchaseHasProductUpdateMapper();
    private final PurchaseHasProductResponseMapper purchaseHasProductResponseMapper = new PurchaseHasProductResponseMapper();

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> add(@Valid @RequestBody PurchaseHasProductAddDTO purchaseHasProduct) throws NoResultsException, InvalidBodyException, NonExistenceException, ForbiddenActionException {
        if (purchaseHasProduct.getId() != null) {
            purchaseAuthorizationService.authorizePurchaseAccess(purchaseHasProduct.getId().getPurchaseId(), ErrorMessages.NO_PURCHASE_RESULTS);
        }
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        response.setData(purchaseHasProductResponseMapper.modelToDto(addPurchaseHasProductUseCase.add(purchaseHasProductAddMapper.dtoToModel(purchaseHasProduct))));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/add/all")
    public ResponseEntity<ApiResponse<List<PurchaseHasProductResponseDTO>>> addAll(@Valid @RequestBody List<PurchaseHasProductAddDTO> purchaseHasProducts) throws NoResultsException, InvalidBodyException, ForbiddenActionException {
        purchaseAuthorizationService.authorizePurchaseHasProductIds(
                purchaseHasProducts.stream().map(PurchaseHasProductAddDTO::getId).toList()
        );
        ApiResponse<List<PurchaseHasProductResponseDTO>> response = new ApiResponse<>();
        response.setData(purchaseHasProductResponseMapper.modelsToDtos(addAllPurchaseHasProductUseCase.addAll(purchaseHasProductAddMapper.dtosToModels(purchaseHasProducts))));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PurchaseHasProductResponseDTO>> update(@Valid @RequestBody PurchaseHasProductUpdateDTO purchaseHasProduct) throws NoResultsException, InvalidBodyException, NoChangesException, NonExistenceException, ForbiddenActionException {
        if (purchaseHasProduct.getId() != null) {
            purchaseAuthorizationService.authorizePurchaseAccess(purchaseHasProduct.getId().getPurchaseId(), ErrorMessages.NO_PURCHASE_RESULTS);
        }
        ApiResponse<PurchaseHasProductResponseDTO> response = new ApiResponse<>();
        response.setData(purchaseHasProductResponseMapper.modelToDto(updatePurchaseHasProductUseCase.update(purchaseHasProductUpdateMapper.dtoToModel(purchaseHasProduct))));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/purchase/{purchaseId}/product/{productId}")
    public ResponseEntity<ApiResponse<Object>> deleteByPurchaseIdAndProductId(@PathVariable Long purchaseId, @PathVariable Long productId) throws NonExistenceException, NoResultsException, ForbiddenActionException {
        purchaseAuthorizationService.authorizePurchaseAccess(purchaseId, ErrorMessages.NO_PURCHASE_RESULTS);
        ApiResponse<Object> response = new ApiResponse<>();
        PurchaseHasProductId id = PurchaseHasProductId.builder()
                .purchaseId(purchaseId)
                .productId(productId)
                .build();
        removeByIdPurchaseHasProductUseCase.removeByPurchaseIdAndProductId(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/remove/all")
    public ResponseEntity<ApiResponse<Object>> deleteByPurchaseIdAndProductId(@Valid @RequestBody List<PurchaseHasProductId> ids) throws NoIdReceivedException, NoResultsException, ForbiddenActionException {
        purchaseAuthorizationService.authorizePurchaseHasProductIds(ids);
        ApiResponse<Object> response = new ApiResponse<>();
        removeAllPurchaseHasProductUseCase.removeAll(ids);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
