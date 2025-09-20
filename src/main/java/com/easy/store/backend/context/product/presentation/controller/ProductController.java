package com.easy.store.backend.context.product.presentation.controller;

import com.easy.store.backend.context.product.application.dto.ProductCreateDTO;
import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.product.application.dto.ProductUpdateDTO;
import com.easy.store.backend.context.product.application.usecase.*;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductCreateMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductResponseMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
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
    private final FindByAccountIdProductUseCase findByAccountIdProductUseCase;
    private final FindByCategoryIdProductUseCase findByCategoryIdProductUseCase;
    private final FindBySubcategoryIdProductUseCase findBySubcategoryIdProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteByIdProductUseCase deleteByIdProductUseCase;
    private final ChangeStateByIdProductUseCase changeStateByIdProductUseCase;

    private final ProductCreateMapper productCreateMapper = new ProductCreateMapper();
    private final ProductUpdateMapper productUpdateMapper = new ProductUpdateMapper();
    private final ProductResponseMapper productResponseMapper = new ProductResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(findAllProductUseCase.findAll());
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> findById(@PathVariable Long id) throws NoResultsException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        Product product = findByIdProductUseCase.findById(id);
        response.setData(productResponseMapper.modelToDto(product));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findByAccountId(@PathVariable Long accountId) throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(findByAccountIdProductUseCase.findByAccountId(accountId));
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findByCategoryId(@PathVariable Long categoryId) throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(findByCategoryIdProductUseCase.findByCategoryId(categoryId));
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findBySubcategoryId(@PathVariable Long subcategoryId) throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(findBySubcategoryIdProductUseCase.findBySubcategoryId(subcategoryId));
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@RequestBody ProductCreateDTO product, @RequestHeader("Create-By") Long createBy) throws NoResultsException, InvalidBodyException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        product.setCreateBy(createBy);
        response.setData(productResponseMapper.modelToDto(createProductUseCase.create(productCreateMapper.dtoToModel(product))));
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(@RequestBody ProductUpdateDTO product, @RequestHeader("Update-By") Long updateBy) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        product.setUpdateBy(updateBy);
        response.setData(productResponseMapper.modelToDto(updateProductUseCase.update(productUpdateMapper.dtoToModel(product))));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdProductUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateDTO>> changeStateById(@PathVariable Long id, @RequestHeader("Update-By") Long updateBy) throws NonExistenceException {
        ApiResponse<ProductUpdateDTO> response = new ApiResponse<>();
        Product product = changeStateByIdProductUseCase.changeStateById(id, updateBy);
        response.setData(productUpdateMapper.modelToDto(product));
        return ResponseEntity.ok(response);
    }

}
