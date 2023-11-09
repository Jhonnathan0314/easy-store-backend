package com.sophie.store.backend.context.purchase.infrastructure.mappers;

import com.sophie.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.sophie.store.backend.context.purchase.data.PurchaseData;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PurchaseResponseMapperTest {

    @InjectMocks
    private PurchaseResponseMapper purchaseResponseMapper;

    private static PurchaseData purchaseData;

    @BeforeAll
    static void setUp() {
        purchaseData = new PurchaseData();
    }

    @Test
    void entityToModelTest() {
        PurchaseEntity purchaseEntity = purchaseData.getPurchaseEntity();

        Purchase purchaseModel = purchaseResponseMapper.entityToModel(purchaseEntity);

        assertNotNull(purchaseModel);
    }

    @Test
    void modelToEntityTest() {
        Purchase purchaseModel = purchaseData.getPurchaseModel();

        PurchaseEntity purchaseEntity = purchaseResponseMapper.modelToEntity(purchaseModel);

        assertNotNull(purchaseEntity);
    }

    @Test
    void modelToDtoTest() {
        Purchase purchaseModel = purchaseData.getPurchaseModel();

        PurchaseResponseDTO purchaseResponseDTO = purchaseResponseMapper.modelToDto(purchaseModel);

        assertNotNull(purchaseResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        PurchaseResponseDTO purchaseResponseDTO = purchaseData.getPurchaseResponseDTO();

        Purchase purchaseModel = purchaseResponseMapper.dtoToModel(purchaseResponseDTO);

        assertNotNull(purchaseModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PurchaseEntity> purchaseEntities = List.of(purchaseData.getPurchaseEntity());

        List<Purchase> purchaseModels = purchaseResponseMapper.entitiesToModels(purchaseEntities);

        assertNotNull(purchaseModels);
        assertEquals(purchaseEntities.size(), purchaseModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<Purchase> purchaseModels = List.of(purchaseData.getPurchaseModel());

        List<PurchaseEntity> purchaseEntities = purchaseResponseMapper.modelsToEntities(purchaseModels);

        assertNotNull(purchaseEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<Purchase> purchaseModels = List.of(purchaseData.getPurchaseModel());

        List<PurchaseResponseDTO> purchaseResponseDTOs = purchaseResponseMapper.modelsToDtos(purchaseModels);

        assertNotNull(purchaseResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PurchaseResponseDTO> purchaseResponseDTOs = List.of(purchaseData.getPurchaseResponseDTO());

        List<Purchase> purchaseModels = purchaseResponseMapper.dtosToModels(purchaseResponseDTOs);

        assertNotNull(purchaseModels);
    }

}