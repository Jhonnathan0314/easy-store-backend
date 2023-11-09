package com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductDTO;
import com.sophie.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PurchaseHasProductMapperTest {

    @InjectMocks
    private PurchaseHasProductMapper purchaseHasProductMapper;

    private static PurchaseHasProductData purchaseHasProductData;

    @BeforeAll
    static void setUp() {
        purchaseHasProductData = new PurchaseHasProductData();
    }

    @Test
    void entityToModelTest() {
        PurchaseHasProductEntity purchaseHasProductEntity = new PurchaseHasProductEntity();
        purchaseHasProductEntity = purchaseHasProductData.getPurchaseHasProductEntity();

        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductMapper.entityToModel(purchaseHasProductEntity);

        assertNotNull(purchaseHasProductModel);
    }

    @Test
    void modelToEntityTest() {
        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductData.getPurchaseHasProductModel();

        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductMapper.modelToEntity(purchaseHasProductModel);

        assertNotNull(purchaseHasProductEntity);
    }

    @Test
    void modelToDtoTest() {
        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductData.getPurchaseHasProductModel();

        PurchaseHasProductDTO purchaseHasProductDTO = purchaseHasProductMapper.modelToDto(purchaseHasProductModel);

        assertNotNull(purchaseHasProductDTO);
    }

    @Test
    void dtoToModelTest() {
        PurchaseHasProductDTO purchaseHasProductDTO = purchaseHasProductData.getPurchaseHasProductDTO();

        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductMapper.dtoToModel(purchaseHasProductDTO);

        assertNotNull(purchaseHasProductModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PurchaseHasProductEntity> purchaseHasProductEntities = List.of(purchaseHasProductData.getPurchaseHasProductEntity());

        List<PurchaseHasProduct> purchaseHasProductModels = purchaseHasProductMapper.entitiesToModels(purchaseHasProductEntities);

        assertNotNull(purchaseHasProductModels);
    }

    @Test
    void modelsToEntitiesTest() {
        List<PurchaseHasProduct> purchaseHasProductModels = List.of(purchaseHasProductData.getPurchaseHasProductModel());

        List<PurchaseHasProductEntity> purchaseHasProductEntities = purchaseHasProductMapper.modelsToEntities(purchaseHasProductModels);

        assertNotNull(purchaseHasProductEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PurchaseHasProduct> purchaseHasProductModels = List.of(purchaseHasProductData.getPurchaseHasProductModel());

        List<PurchaseHasProductDTO> purchaseHasProductDTOs = purchaseHasProductMapper.modelsToDtos(purchaseHasProductModels);

        assertNotNull(purchaseHasProductDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PurchaseHasProductDTO> purchaseHasProductDTOs = List.of(purchaseHasProductData.getPurchaseHasProductDTO());

        List<PurchaseHasProduct> purchaseHasProductModels = purchaseHasProductMapper.dtosToModels(purchaseHasProductDTOs);

        assertNotNull(purchaseHasProductModels);
    }

}