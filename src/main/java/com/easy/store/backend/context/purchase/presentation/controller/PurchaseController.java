package com.easy.store.backend.context.purchase.presentation.controller;

import com.easy.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.easy.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.easy.store.backend.context.purchase.application.dto.PurchaseUpdateDTO;
import com.easy.store.backend.context.purchase.application.usecase.*;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseGenerateMapper;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseResponseMapper;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PurchaseController {

    private final FindAllPurchaseUseCase findAllPurchaseUseCase;
    private final FindByIdPurchaseUseCase findByIdPurchaseUseCase;
    private final FindByAccountIdPurchaseUseCase findByAccountIdPurchaseUseCase;
    private final FindByCategoryIdPurchaseUseCase findByCategoryIdPurchaseUseCase;
    private final FindByUserIdPurchaseUseCase findByUserIdPurchaseUseCase;
    private final GeneratePurchaseUseCase generatePurchaseUseCase;
    private final UpdatePurchaseUseCase updatePurchaseUseCase;
    private final DeleteByIdPurchaseUseCase deleteByIdPurchaseUseCase;

    private final PurchaseGenerateMapper purchaseGenerateMapper = new PurchaseGenerateMapper();
    private final PurchaseUpdateMapper purchaseUpdateMapper = new PurchaseUpdateMapper();
    private final PurchaseResponseMapper purchaseResponseMapper = new PurchaseResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findAllPurchaseUseCase.findAll());
        response.setData(purchases);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> findById(@PathVariable Long id) throws NoResultsException {
        ApiResponse<PurchaseResponseDTO> response = new ApiResponse<>();
        Purchase purchase = findByIdPurchaseUseCase.findById(id);
        response.setData(purchaseResponseMapper.modelToDto(purchase));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findByAccountId(@PathVariable Long accountId) throws NoResultsException {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByAccountIdPurchaseUseCase.findByAccountId(accountId));
        response.setData(purchases);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findByCategoryId(@PathVariable Long categoryId) throws NoResultsException {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByCategoryIdPurchaseUseCase.findByCategoryId(categoryId));
        response.setData(purchases);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> findByUserId(@PathVariable Long userId) throws NoResultsException {
        ApiResponse<List<PurchaseResponseDTO>> response = new ApiResponse<>();
        List<PurchaseResponseDTO> purchases = purchaseResponseMapper.modelsToDtos(findByUserIdPurchaseUseCase.findByUserId(userId));
        response.setData(purchases);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> generate(@RequestBody PurchaseGenerateDTO purchase,
                                                                     @RequestHeader("Create-By") Long createBy) throws NoResultsException, InvalidBodyException {
        ApiResponse<PurchaseResponseDTO> response = new ApiResponse<>();
        purchase.setCreateBy(createBy);
        response.setData(purchaseResponseMapper.modelToDto(generatePurchaseUseCase.generate(purchaseGenerateMapper.dtoToModel(purchase))));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> update(@RequestBody PurchaseUpdateDTO purchase,
                                                                     @RequestHeader("Update-By") Long updateBy) throws NoResultsException, NoIdReceivedException, InvalidBodyException, NoChangesException, NonExistenceException {
        ApiResponse<PurchaseResponseDTO> response = new ApiResponse<>();
        purchase.setUpdateBy(updateBy);
        response.setData(purchaseResponseMapper.modelToDto(updatePurchaseUseCase.update(purchaseUpdateMapper.dtoToModel(purchase))));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) throws InvalidActionException, NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdPurchaseUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
