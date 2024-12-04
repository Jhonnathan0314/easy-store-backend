package com.easy.store.backend.context.purchase.infrastructure.mappers;

import com.easy.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PurchaseGenerateMapperTest {

    @InjectMocks
    private PurchaseGenerateMapper purchaseGenerateMapper;

    private static PurchaseData purchaseData;

    @BeforeAll
    static void setUp() {
        purchaseData = new PurchaseData();
    }

    @Test
    void entityToModelTest() {
        PurchaseEntity purchaseEntity = purchaseData.getPurchaseEntity();

        Purchase purchaseModel = purchaseGenerateMapper.entityToModel(purchaseEntity);

        assertNotNull(purchaseModel);
    }

    @Test
    void modelToEntityTest() {
        Purchase purchaseModel = purchaseData.getPurchaseModel();

        PurchaseEntity purchaseEntity = purchaseGenerateMapper.modelToEntity(purchaseModel);

        assertNotNull(purchaseEntity);
    }

    @Test
    void modelToDtoTest() {
        Purchase purchaseModel = purchaseData.getPurchaseModel();

        PurchaseGenerateDTO purchaseGenerateDTO = purchaseGenerateMapper.modelToDto(purchaseModel);

        assertNotNull(purchaseGenerateDTO);
    }

    @Test
    void dtoToModelTest() {
        PurchaseGenerateDTO purchaseGenerateDTO = purchaseData.getPurchaseGenerateDTO();

        Purchase purchaseModel = purchaseGenerateMapper.dtoToModel(purchaseGenerateDTO);

        assertNotNull(purchaseModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PurchaseEntity> purchaseEntities = List.of(purchaseData.getPurchaseEntity());

        List<Purchase> purchaseModels = purchaseGenerateMapper.entitiesToModels(purchaseEntities);

        assertNotNull(purchaseModels);
        assertEquals(purchaseEntities.size(), purchaseModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Purchase> purchaseModels = List.of(purchaseData.getPurchaseModel());

        List<PurchaseEntity> purchaseEntities = purchaseGenerateMapper.modelsToEntities(purchaseModels);

        assertNotNull(purchaseEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Purchase> purchaseModels = List.of(purchaseData.getPurchaseModel());

        List<PurchaseGenerateDTO> purchaseGenerateDTOs = purchaseGenerateMapper.modelsToDtos(purchaseModels);

        assertNotNull(purchaseGenerateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PurchaseGenerateDTO> purchaseGenerateDTOs = List.of(purchaseData.getPurchaseGenerateDTO());

        List<Purchase> purchaseModels = purchaseGenerateMapper.dtosToModels(purchaseGenerateDTOs);

        assertNotNull(purchaseModels);
    }

}