package com.sophie.store.backend.context.purchase_has_product.infrastructure.mappers;

import com.sophie.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.sophie.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PurchaseHasProductResponseMapperTest {

    @InjectMocks
    private PurchaseHasProductResponseMapper purchaseHasProductResponseMapper;

    private static PurchaseHasProductData purchaseHasProductData;

    @BeforeAll
    static void setUp() {
        purchaseHasProductData = new PurchaseHasProductData();
    }

    @Test
    void entityToModelTest() {
        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductData.getPurchaseHasProductEntity();

        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductResponseMapper.entityToModel(purchaseHasProductEntity);

        assertNotNull(purchaseHasProductModel);
    }

    @Test
    void modelToEntityTest() {
        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductData.getPurchaseHasProductModel();

        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductResponseMapper.modelToEntity(purchaseHasProductModel);

        assertNotNull(purchaseHasProductEntity);
    }

    @Test
    void modelToDtoTest() {
        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductData.getPurchaseHasProductModel();

        PurchaseHasProductResponseDTO purchaseHasProductResponseDTO = purchaseHasProductResponseMapper.modelToDto(purchaseHasProductModel);

        assertNotNull(purchaseHasProductResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        PurchaseHasProductResponseDTO purchaseHasProductResponseDTO = purchaseHasProductData.getPurchaseHasProductResponseDTO();

        PurchaseHasProduct purchaseHasProductModel = purchaseHasProductResponseMapper.dtoToModel(purchaseHasProductResponseDTO);

        assertNotNull(purchaseHasProductModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PurchaseHasProductEntity> purchaseHasProductEntities = List.of(purchaseHasProductData.getPurchaseHasProductEntity());

        List<PurchaseHasProduct> purchaseHasProductModels = purchaseHasProductResponseMapper.entitiesToModels(purchaseHasProductEntities);

        assertNotNull(purchaseHasProductModels);
        assertEquals(purchaseHasProductEntities.size(), purchaseHasProductModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<PurchaseHasProduct> purchaseHasProductModels = List.of(purchaseHasProductData.getPurchaseHasProductModel());

        List<PurchaseHasProductEntity> purchaseHasProductEntities = purchaseHasProductResponseMapper.modelsToEntities(purchaseHasProductModels);

        assertNotNull(purchaseHasProductEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PurchaseHasProduct> purchaseHasProductModels = List.of(purchaseHasProductData.getPurchaseHasProductModel());

        List<PurchaseHasProductResponseDTO> purchaseHasProductResponseDTOs = purchaseHasProductResponseMapper.modelsToDtos(purchaseHasProductModels);

        assertNotNull(purchaseHasProductResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PurchaseHasProductResponseDTO> purchaseHasProductResponseDTOs = List.of(purchaseHasProductData.getPurchaseHasProductResponseDTO());

        List<PurchaseHasProduct> purchaseHasProductModels = purchaseHasProductResponseMapper.dtosToModels(purchaseHasProductResponseDTOs);

        assertNotNull(purchaseHasProductModels);
    }

}