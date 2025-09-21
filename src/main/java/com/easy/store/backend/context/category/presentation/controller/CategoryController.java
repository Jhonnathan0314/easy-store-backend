package com.easy.store.backend.context.category.presentation.controller;

import com.easy.store.backend.context.category.application.dto.CategoryCreateDTO;
import com.easy.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.easy.store.backend.context.category.application.dto.CategoryUpdateDTO;
import com.easy.store.backend.context.category.application.usecase.*;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryCreateMapper;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryResponseMapper;
import com.easy.store.backend.context.category.infrastructure.mappers.CategoryUpdateMapper;
import com.easy.store.backend.context.s3.model.S3File;
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

    private final FindAllCategoryUseCase findAllCategory;
    private final FindByIdCategoryUseCase findByIdCategory;
    private final FindByAccountIdCategoryUseCase findByAccountIdCategory;
    private final CreateCategoryUseCase createCategory;
    private final UpdateCategoryUseCase updateCategory;
    private final UpdateImgCategoryUseCase updateImgCategory;
    private final DeleteByIdCategoryUseCase deleteByIdCategory;
    private final ChangeStateByIdCategoryUseCase changeStateByIdCategory;

    private final CategoryCreateMapper categoryCreateMapper = new CategoryCreateMapper();
    private final CategoryUpdateMapper categoryUpdateMapper = new CategoryUpdateMapper();
    private final CategoryResponseMapper categoryResponseMapper = new CategoryResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findAll(
            @RequestParam(required = false) Boolean image
    ) throws NoResultsException, FileException {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        boolean loadImage = Boolean.TRUE.equals(image);
        List<CategoryResponseDTO> categories = categoryResponseMapper.modelsToDtos(findAllCategory.findAll(loadImage));
        response.setData(categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> findById(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean image
    ) throws NoResultsException, FileException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        boolean loadImage = Boolean.TRUE.equals(image);
        Category category = findByIdCategory.findById(id, loadImage);
        response.setData(categoryResponseMapper.modelToDto(category));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findByAccountId(
            @PathVariable Long accountId,
            @RequestParam(required = false) Boolean image
    ) throws NoResultsException, FileException {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        boolean loadImage = Boolean.TRUE.equals(image);
        List<Category> categories = findByAccountIdCategory.findByAccountId(accountId, loadImage);
        response.setData(categoryResponseMapper.modelsToDtos(categories));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(
            @RequestBody CategoryCreateDTO category,
            @RequestHeader("Create-By") Long createBy
    ) throws NoIdReceivedException, InvalidBodyException, DuplicatedException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        category.setCreateBy(createBy);
        Category categoryModel = createCategory.create(categoryCreateMapper.dtoToModel(category));
        response.setData(categoryResponseMapper.modelToDto(categoryModel));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(
            @RequestBody CategoryUpdateDTO category,
            @RequestHeader("Update-By") Long updateBy
    ) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        category.setUpdateBy(updateBy);
        Category updatedCategoryModel = updateCategory.update(categoryUpdateMapper.dtoToModel(category));
        response.setData(categoryResponseMapper.modelToDto(updatedCategoryModel));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/img")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategoryImg(
            @PathVariable Long id,
            @RequestBody S3File img,
            @RequestHeader("Update-By") Long updateBy
    ) throws NoChangesException, NonExistenceException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        Category categoryModel = updateImgCategory.updateCategoryImg(id, img, updateBy);
        response.setData(categoryResponseMapper.modelToDto(categoryModel));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(
            @PathVariable Long id
    ) throws NonExistenceException {
        deleteByIdCategory.deleteById(id);
        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<Object>> changeStateById(
            @PathVariable Long id,
            @RequestHeader("Update-By") Long updateBy
    ) throws NonExistenceException {
        changeStateByIdCategory.changeStateById(id, updateBy);
        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.NO_CONTENT);
    }

}
