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
import com.easy.store.backend.utils.http.HttpUtils;
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
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteByIdCategoryUseCase deleteByIdCategoryUseCase;
    private final ChangeStateByIdCategoryUseCase changeStateByIdCategoryUseCase;

    private final CategoryCreateMapper categoryCreateMapper = new CategoryCreateMapper();
    private final CategoryUpdateMapper categoryUpdateMapper = new CategoryUpdateMapper();
    private final CategoryResponseMapper categoryResponseMapper = new CategoryResponseMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findAll() {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        try {
            List<CategoryResponseDTO> categories = categoryResponseMapper.modelsToDtos(findAllCategoryUseCase.findAll());
            response.setData(categories);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        try {
            Category category = findByIdCategoryUseCase.findById(id);
            response.setData(categoryResponseMapper.modelToDto(category));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(@RequestBody CategoryCreateDTO category, @RequestHeader("Create-By") Long createBy) {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        try {
            category.setCreateBy(createBy);
            response.setData(categoryResponseMapper.modelToDto(createCategoryUseCase.create(categoryCreateMapper.dtoToModel(category))));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | NoIdReceivedException | InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(@RequestBody CategoryUpdateDTO category, @RequestHeader("Update-By") Long updateBy) {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        try {
            category.setUpdateBy(updateBy);
            response.setData(categoryResponseMapper.modelToDto(updateCategoryUseCase.update(categoryUpdateMapper.dtoToModel(category))));
            return ResponseEntity.ok(response);
        } catch (NoIdReceivedException | InvalidBodyException | NoResultsException | NoChangesException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdCategoryUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<CategoryUpdateDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<CategoryUpdateDTO> response = new ApiResponse<>();
        try {
            Category category = changeStateByIdCategoryUseCase.changeStateById(id);
            response.setData(categoryUpdateMapper.modelToDto(category));
            return ResponseEntity.ok(response);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
