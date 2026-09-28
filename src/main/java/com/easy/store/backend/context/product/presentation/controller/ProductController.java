package com.easy.store.backend.context.product.presentation.controller;

import com.easy.store.backend.context.product.application.dto.ProductCreateDTO;
import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.product.application.dto.ProductUpdateDTO;
import com.easy.store.backend.context.product.application.usecase.*;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductCreateMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductResponseMapper;
import com.easy.store.backend.context.product.infrastructure.mappers.ProductUpdateMapper;
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
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final FindAllProductUseCase findAllProduct;
    private final FindByIdProductUseCase findByIdProduct;
    private final FindByAccountIdProductUseCase findByAccountIdProduct;
    private final FindByCategoryIdProductUseCase findByCategoryIdProduct;
    private final GetProductImageUseCase getProductImageUseCase;
    private final CreateProductUseCase createProduct;
    private final UpdateProductUseCase updateProduct;
    private final AddImageProductUseCase addImageProduct;
    private final DeleteImageProductUseCase deleteImageProduct;
    private final DeleteByIdProductUseCase deleteByIdProduct;
    private final ChangeStateByIdProductUseCase changeStateByIdProduct;

    private final ProductCreateMapper productCreateMapper = new ProductCreateMapper();
    private final ProductUpdateMapper productUpdateMapper = new ProductUpdateMapper();
    private final ProductResponseMapper productResponseMapper = new ProductResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(findAllProduct.findAll());
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> findById(
            @PathVariable Long id
    ) throws NoResultsException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        Product model = findByIdProduct.findById(id);
        response.setData(productResponseMapper.modelToDto(model));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findByAccountId(
            @PathVariable Long accountId
    ) throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<Product> models = findByAccountIdProduct.findByAccountId(accountId);
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(models);
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> findByCategoryId(
            @PathVariable Long categoryId
    ) throws NoResultsException {
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
        List<Product> models = findByCategoryIdProduct.findByCategoryId(categoryId);
        List<ProductResponseDTO> products = productResponseMapper.modelsToDtos(models);
        response.setData(products);
        return ResponseEntity.ok(response);
    }

    /**
     * Sirve la imagen como recurso HTTP propio (Content-Type + Cache-Control + ETag) en vez de
     * embeberla en el JSON del listado: evita una llamada a S3 por imagen en cada request de
     * listado, y habilita cache real del navegador. Una imagen inexistente/ilegible responde 404
     * (no 500) para no romper la vista que la muestra.
     */
    @GetMapping("/{productId}/image/{imageName}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable Long productId,
            @PathVariable String imageName,
            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false) String ifNoneMatch
    ) {
        Optional<S3ObjectContent> imageOpt = getProductImageUseCase.getImage(productId, imageName);
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

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(
            @Valid @RequestBody ProductCreateDTO product,
            @RequestHeader("Create-By") Long createBy
    ) throws NoResultsException, InvalidBodyException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        product.setCreateBy(createBy);
        Product model = createProduct.create(productCreateMapper.dtoToModel(product));
        response.setData(productResponseMapper.modelToDto(model));
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(
            @Valid @RequestBody ProductUpdateDTO product,
            @RequestHeader("Update-By") Long updateBy
    ) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        product.setUpdateBy(updateBy);
        Product model = updateProduct.update(productUpdateMapper.dtoToModel(product));
        response.setData(productResponseMapper.modelToDto(model));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/delete-images")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> deleteImagesById(
            @PathVariable Long id,
            @RequestBody List<S3File> images,
            @RequestHeader("Update-By") Long updateBy
    ) throws NonExistenceException, NoResultsException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        Product model = deleteImageProduct.deleteImages(id, images, updateBy);
        response.setData(productResponseMapper.modelToDto(model));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/upload-images")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> uploadImagesById(
            @PathVariable Long id,
            @RequestBody List<S3File> images,
            @RequestHeader("Update-By") Long updateBy
    ) throws NonExistenceException, InvalidActionException, NoResultsException, FileException {
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>();
        Product model = addImageProduct.addImages(id, images, updateBy);
        response.setData(productResponseMapper.modelToDto(model));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(
            @PathVariable Long id
    ) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdProduct.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateDTO>> changeStateById(
            @PathVariable Long id,
            @RequestHeader("Update-By") Long updateBy
    ) throws NonExistenceException {
        ApiResponse<ProductUpdateDTO> response = new ApiResponse<>();
        Product product = changeStateByIdProduct.changeStateById(id, updateBy);
        response.setData(productUpdateMapper.modelToDto(product));
        return ResponseEntity.ok(response);
    }

}
