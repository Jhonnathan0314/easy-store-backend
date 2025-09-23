package com.easy.store.backend.context.category_has_payment_type.presentation.controller;

import com.easy.store.backend.context.category_has_payment_type.application.dto.CategoryHasPaymentTypeCreateDto;
import com.easy.store.backend.context.category_has_payment_type.application.dto.CategoryHasPaymentTypeResponseDto;
import com.easy.store.backend.context.category_has_payment_type.application.dto.CategoryHasPaymentTypeUpdateDto;
import com.easy.store.backend.context.category_has_payment_type.application.usecase.*;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeCreateMapper;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeResponseMapper;
import com.easy.store.backend.context.category_has_payment_type.infrastructure.mapper.CategoryHasPaymentTypeUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category-has-payment-type")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CategoryHasPaymentTypeController {

    private final FindAllCategoryHasPaymentTypeUseCase findAllCategoryHasPaymentTypeUseCase;
    private final FindByAccountIdCategoryHasPaymentTypeUseCase findByAccountIdCategoryHasPaymentTypeUseCase;
    private final FindByCategoryIdCategoryHasPaymentTypeUseCase findByCategoryIdCategoryHasPaymentTypeUseCase;
    private final FindByIdCategoryHasPaymentTypeUseCase findByIdCategoryHasPaymentTypeUseCase;
    private final CreateCategoryHasPaymentTypeUseCase createCategoryHasPaymentTypeUseCase;
    private final UpdateCategoryHasPaymentTypeUseCase updateCategoryHasPaymentTypeUseCase;
    private final ChangeStateByIdCategoryHasPaymentTypeUseCase changeStateByIdCategoryHasPaymentTypeUseCase;

    private final CategoryHasPaymentTypeResponseMapper responseMapper = new CategoryHasPaymentTypeResponseMapper();
    private final CategoryHasPaymentTypeCreateMapper createMapper = new CategoryHasPaymentTypeCreateMapper();
    private final CategoryHasPaymentTypeUpdateMapper updateMapper = new CategoryHasPaymentTypeUpdateMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryHasPaymentTypeResponseDto>>> findAll() throws NoResultsException {
        ApiResponse<List<CategoryHasPaymentTypeResponseDto>> response = new ApiResponse<>();
        List<CategoryHasPaymentTypeResponseDto> dtos = responseMapper.modelsToDtos(findAllCategoryHasPaymentTypeUseCase.findAll());
        response.setData(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/account/{accountId}")
    public ResponseEntity<ApiResponse<List<CategoryHasPaymentTypeResponseDto>>> findActiveByAccountId(@PathVariable Long accountId) throws NoResultsException {
        ApiResponse<List<CategoryHasPaymentTypeResponseDto>> response = new ApiResponse<>();
        List<CategoryHasPaymentTypeResponseDto> dtos = responseMapper.modelsToDtos(findByAccountIdCategoryHasPaymentTypeUseCase.findByAccountId(accountId));
        response.setData(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<CategoryHasPaymentTypeResponseDto>>> findActiveByCategoryId(@PathVariable Long categoryId) throws NoResultsException {
        ApiResponse<List<CategoryHasPaymentTypeResponseDto>> response = new ApiResponse<>();
        List<CategoryHasPaymentTypeResponseDto> dtos = responseMapper.modelsToDtos(findByCategoryIdCategoryHasPaymentTypeUseCase.findByCategoryId(categoryId));
        response.setData(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}/payment-type/{paymentTypeId}")
    public ResponseEntity<ApiResponse<CategoryHasPaymentTypeResponseDto>> findById(@PathVariable Long categoryId, @PathVariable Long paymentTypeId) throws NoResultsException {
        ApiResponse<CategoryHasPaymentTypeResponseDto> response = new ApiResponse<>();
        CategoryHasPaymentTypeId id = CategoryHasPaymentTypeId.builder()
                .categoryId(categoryId)
                .paymentTypeId(paymentTypeId)
                .build();
        CategoryHasPaymentTypeResponseDto dto = responseMapper.modelToDto(findByIdCategoryHasPaymentTypeUseCase.findById(id));
        response.setData(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryHasPaymentTypeResponseDto>> create(@RequestBody CategoryHasPaymentTypeCreateDto createDto) throws NoResultsException, NoIdReceivedException, InvalidBodyException {
        ApiResponse<CategoryHasPaymentTypeResponseDto> response = new ApiResponse<>();
        CategoryHasPaymentType model = createMapper.dtoToModel(createDto);
        CategoryHasPaymentTypeResponseDto dto = responseMapper.modelToDto(createCategoryHasPaymentTypeUseCase.create(model));
        response.setData(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryHasPaymentTypeResponseDto>> update(@RequestBody CategoryHasPaymentTypeUpdateDto updateDto) throws NoResultsException, NoIdReceivedException, InvalidBodyException, NoChangesException {
        ApiResponse<CategoryHasPaymentTypeResponseDto> response = new ApiResponse<>();
        CategoryHasPaymentType model = updateMapper.dtoToModel(updateDto);
        CategoryHasPaymentTypeResponseDto dto = responseMapper.modelToDto(updateCategoryHasPaymentTypeUseCase.update(model));
        response.setData(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/state")
    public ResponseEntity<ApiResponse<CategoryHasPaymentTypeResponseDto>> changeStateById(@RequestBody CategoryHasPaymentTypeId id) throws NonExistenceException {
        ApiResponse<CategoryHasPaymentTypeResponseDto> response = new ApiResponse<>();
        CategoryHasPaymentTypeResponseDto dto = responseMapper.modelToDto(changeStateByIdCategoryHasPaymentTypeUseCase.changeStateByIdCategoryHasPaymentTypeUseCase(id));
        response.setData(dto);
        return ResponseEntity.ok(response);
    }

}
