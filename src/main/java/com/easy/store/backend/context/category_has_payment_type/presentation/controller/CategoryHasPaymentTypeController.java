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

@RestController
@RequestMapping("/api/v1/category-has-payment-type")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CategoryHasPaymentTypeController {

    private final CreateCategoryHasPaymentTypeUseCase createCategoryHasPaymentTypeUseCase;
    private final UpdateCategoryHasPaymentTypeUseCase updateCategoryHasPaymentTypeUseCase;
    private final ChangeStateByIdCategoryHasPaymentTypeUseCase changeStateByIdCategoryHasPaymentTypeUseCase;
    private final DeleteCategoryHasPaymentTypeUseCase deleteCategoryHasPaymentTypeUseCase;

    private final CategoryHasPaymentTypeResponseMapper responseMapper = new CategoryHasPaymentTypeResponseMapper();
    private final CategoryHasPaymentTypeCreateMapper createMapper = new CategoryHasPaymentTypeCreateMapper();
    private final CategoryHasPaymentTypeUpdateMapper updateMapper = new CategoryHasPaymentTypeUpdateMapper();

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

    @DeleteMapping("/category/{categoryId}/payment-type/{paymentTypeId}")
    public ResponseEntity<ApiResponse<Object>> deleteById(
            @PathVariable Long categoryId,
            @PathVariable Long paymentTypeId
    ) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteCategoryHasPaymentTypeUseCase.deleteById(categoryId, paymentTypeId);
        response.setData(null);
        return ResponseEntity.ok(response);
    }

}
