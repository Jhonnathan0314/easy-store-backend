package com.sophie.store.backend.context.product.infrastructure.mappers;

import com.sophie.store.backend.context.product.application.dto.ProductResponseDTO;
import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductResponseMapperTest {

    @InjectMocks
    private ProductResponseMapper productResponseMapper;

    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
    }

    @Test
    void entityToModelTest() {
        ProductEntity productEntity = productData.getProductEntity();

        Product productModel = productResponseMapper.entityToModel(productEntity);

        assertNotNull(productModel);
    }

    @Test
    void modelToEntityTest() {
        Product productModel = productData.getProductModel();

        ProductEntity productEntity = productResponseMapper.modelToEntity(productModel);

        assertNotNull(productEntity);
    }

    @Test
    void modelToDtoTest() {
        Product productModel = productData.getProductModel();

        ProductResponseDTO productResponseDTO = productResponseMapper.modelToDto(productModel);

        assertNotNull(productResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        ProductResponseDTO productResponseDTO = productData.getProductResponseDTO();

        Product productModel = productResponseMapper.dtoToModel(productResponseDTO);

        assertNotNull(productModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<ProductEntity> productEntities = List.of(productData.getProductEntity());

        List<Product> productModels = productResponseMapper.entitiesToModels(productEntities);

        assertNotNull(productModels);
        assertEquals(productEntities.size(), productModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductEntity> productEntities = productResponseMapper.modelsToEntities(productModels);

        assertNotNull(productEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Product> productModels = List.of(productData.getProductModel());

        List<ProductResponseDTO> productResponseDTOs = productResponseMapper.modelsToDtos(productModels);

        assertNotNull(productResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<ProductResponseDTO> productResponseDTOs = List.of(productData.getProductResponseDTO());

        List<Product> productModels = productResponseMapper.dtosToModels(productResponseDTOs);

        assertNotNull(productModels);
    }

}