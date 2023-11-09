package com.sophie.store.backend.context.product.presentation.controller;

import com.sophie.store.backend.context.product.application.dto.ProductCreateDTO;
import com.sophie.store.backend.context.product.application.dto.ProductResponseDTO;
import com.sophie.store.backend.context.product.application.dto.ProductUpdateDTO;
import com.sophie.store.backend.context.product.application.usecase.*;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductCreateMapper;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductResponseMapper;
import com.sophie.store.backend.context.product.infrastructure.mappers.ProductUpdateMapper;
import com.sophie.store.backend.utils.exceptions.*;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final FindAllProductUseCase findAllProductUseCase;
    private final FindByIdProductUseCase findByIdProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteByIdProductUseCase deleteByIdProductUseCase;
    private final ChangeStateByIdProductUseCase changeStateByIdProductUseCase;

    private final ProductCreateMapper productCreateMapper = new ProductCreateMapper();
    private final ProductUpdateMapper productUpdateMapper = new ProductUpdateMapper();
    private final ProductResponseMapper productResponseMapper = new ProductResponseMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findAll() {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        try {
            List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(findAllProductUseCase.findAll());
            response.setData(products);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        try {
            Product product = findByIdProductUseCase.findById(id);
            response.setData(productResponseMapper.modelToDto(product));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping("/subcategory/{idSubcategory}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@RequestBody ProductCreateDTO product, @PathVariable Long idSubcategory, @RequestHeader("Create-By") Long createBy) {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        try {
            product.setCreateBy(createBy);
            response.setData(productResponseMapper.modelToDto(createProductUseCase.create(productCreateMapper.dtoToModel(product), idSubcategory)));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException | NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping("/subcategory/{idSubcategory}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(@RequestBody ProductUpdateDTO product, @PathVariable Long idSubcategory, @RequestHeader("Update-By") Long updateBy) {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        try {
            product.setUpdateBy(updateBy);
            response.setData(productResponseMapper.modelToDto(updateProductUseCase.update(productUpdateMapper.dtoToModel(product), idSubcategory)));
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
            deleteByIdProductUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<ProductUpdateDTO> response = new ApiResponse<>();
        try {
            Product product = changeStateByIdProductUseCase.changeStateById(id);
            response.setData(productUpdateMapper.modelToDto(product));
            return ResponseEntity.ok(response);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}
