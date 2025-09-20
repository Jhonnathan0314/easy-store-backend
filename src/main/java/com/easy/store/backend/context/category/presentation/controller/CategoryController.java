package com.easy.store.backend.context.category.presentation.controller;

import com.easy.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.easy.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.easy.store.backend.context.category.application.dto.CategoryUpdateDTO;
import com.easy.store.backend.context.category.application.usecase.*;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryCreateMapper;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryResponseMapper;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final FindAllCategoryUseCase findAllCategoryUseCase;
    private final FindByIdCategoryUseCase findByIdCategoryUseCase;
    private final FindByAccountIdCategoryUseCase findByAccountIdCategoryUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteByIdCategoryUseCase deleteByIdCategoryUseCase;
    private final ChangeStateByIdCategoryUseCase changeStateByIdCategoryUseCase;

    private final CategoryCreateMapper categoryCreateMapper = new CategoryCreateMapper();
    private final CategoryUpdateMapper categoryUpdateMapper = new CategoryUpdateMapper();
    private final CategoryResponseMapper categoryResponseMapper = new CategoryResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        List<CategoryResponseDTO> categories = categoryResponseMapper.modelsToDtos(findAllCategoryUseCase.findAll());
        response.setData(categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> findById(@PathVariable Long id) throws NoResultsException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        Category category = findByIdCategoryUseCase.findById(id);
        response.setData(categoryResponseMapper.modelToDto(category));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findByAccountId(@PathVariable Long accountId) throws NoResultsException {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        List<Category> categories = findByAccountIdCategoryUseCase.findByAccountId(accountId);
        response.setData(categoryResponseMapper.modelsToDtos(categories));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@RequestBody CategoryCreateDTO category, @RequestHeader("Create-By") Long createBy) throws NoIdReceivedException, InvalidBodyException, DuplicatedException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        category.setCreateBy(createBy);
        response.setData(categoryResponseMapper.modelToDto(createCategoryUseCase.create(categoryCreateMapper.dtoToModel(category))));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(@RequestBody CategoryUpdateDTO category, @RequestHeader("Update-By") Long updateBy) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        category.setUpdateBy(updateBy);
        response.setData(categoryResponseMapper.modelToDto(updateCategoryUseCase.update(categoryUpdateMapper.dtoToModel(category))));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdCategoryUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<CategoryUpdateDTO>> changeStateById(@PathVariable Long id, @RequestHeader("Update-By") Long updateBy) throws NonExistenceException {
        ApiResponse<CategoryUpdateDTO> response = new ApiResponse<>();
        Category category = changeStateByIdCategoryUseCase.changeStateById(id, updateBy);
        response.setData(categoryUpdateMapper.modelToDto(category));
        return ResponseEntity.ok(response);
    }

}
