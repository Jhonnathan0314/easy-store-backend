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
import com.easy.store.backend.context.s3.model.S3ObjectContent;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final FindAllCategoryUseCase findAllCategory;
    private final FindByIdCategoryUseCase findByIdCategory;
    private final FindByAccountIdCategoryUseCase findByAccountIdCategory;
    private final GetCategoryImageUseCase getCategoryImageUseCase;
    private final CreateCategoryUseCase createCategory;
    private final UpdateCategoryUseCase updateCategory;
    private final UpdateImgCategoryUseCase updateImgCategory;
    private final DeleteByIdCategoryUseCase deleteByIdCategory;
    private final ChangeStateByIdCategoryUseCase changeStateByIdCategory;

    private final CategoryCreateMapper categoryCreateMapper = new CategoryCreateMapper();
    private final CategoryUpdateMapper categoryUpdateMapper = new CategoryUpdateMapper();
    private final CategoryResponseMapper categoryResponseMapper = new CategoryResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        List<CategoryResponseDTO> categories = categoryResponseMapper.modelsToDtos(findAllCategory.findAll());
        response.setData(categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> findById(
            @PathVariable Long id
    ) throws NoResultsException {
        ApiResponse<CategoryResponseDTO> response = new ApiResponse<>();
        Category category = findByIdCategory.findById(id);
        response.setData(categoryResponseMapper.modelToDto(category));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> findByAccountId(
            @PathVariable Long accountId
    ) throws NoResultsException {
        ApiResponse<List<CategoryResponseDTO>> response = new ApiResponse<>();
        List<Category> categories = findByAccountIdCategory.findByAccountId(accountId);
        response.setData(categoryResponseMapper.modelsToDtos(categories));
        return ResponseEntity.ok(response);
    }

    /**
     * Sirve la imagen como recurso HTTP propio (Content-Type + Cache-Control + ETag) en vez de
     * embeberla en el JSON del listado: evita una llamada a S3 por categoria en cada request de
     * listado, y habilita cache real del navegador. Una imagen inexistente/ilegible responde 404
     * (no 500) para no romper la vista que la muestra.
     */
    @GetMapping("/{categoryId}/image/{imageName}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable Long categoryId,
            @PathVariable String imageName,
            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false) String ifNoneMatch
    ) {
        Optional<S3ObjectContent> imageOpt = getCategoryImageUseCase.getImage(categoryId, imageName);
        if (imageOpt.isEmpty()) return ResponseEntity.notFound().build();

        S3ObjectContent image = imageOpt.get();
        if (ifNoneMatch != null && ifNoneMatch.equals(image.getEtag())) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(image.getEtag()).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                .eTag(image.getEtag())
                .body(image.getBytes());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(
            @Valid @RequestBody CategoryCreateDTO category,
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
            @Valid @RequestBody CategoryUpdateDTO category,
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
    ) throws NoChangesException, NonExistenceException, FileException {
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
