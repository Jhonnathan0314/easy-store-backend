package com.sophie.store.backend.context.payment_type.infrastructure.mappers;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.sophie.store.backend.context.payment_type.data.PaymentTypeData;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentTypeResponseMapperTest {

    @InjectMocks
    private PaymentTypeResponseMapper paymentTypeResponseMapper;

    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
    }

    @Test
    void entityToModelTest() {
        PaymentTypeEntity paymentTypeEntity = paymentTypeData.getPaymentTypeEntity();

        PaymentType paymentTypeModel = paymentTypeResponseMapper.entityToModel(paymentTypeEntity);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void modelToEntityTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeEntity paymentTypeEntity = paymentTypeResponseMapper.modelToEntity(paymentTypeModel);

        assertNotNull(paymentTypeEntity);
    }

    @Test
    void modelToDtoTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeResponseDTO paymentTypeResponseDTO = paymentTypeResponseMapper.modelToDto(paymentTypeModel);

        assertNotNull(paymentTypeResponseDTO);
    }

    @Test
    void dtoToModelTest() {
        PaymentTypeResponseDTO paymentTypeResponseDTO = paymentTypeData.getPaymentTypeResponseDTO();

        PaymentType paymentTypeModel = paymentTypeResponseMapper.dtoToModel(paymentTypeResponseDTO);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PaymentTypeEntity> paymentTypeEntities = List.of(paymentTypeData.getPaymentTypeEntity());

        List<PaymentType> paymentTypeModels = paymentTypeResponseMapper.entitiesToModels(paymentTypeEntities);

        assertNotNull(paymentTypeModels);
        assertEquals(paymentTypeEntities.size(), paymentTypeModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeResponseMapper.modelsToEntities(paymentTypeModels);

        assertNotNull(paymentTypeEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeResponseDTO> paymentTypeResponseDTOs = paymentTypeResponseMapper.modelsToDtos(paymentTypeModels);

        assertNotNull(paymentTypeResponseDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PaymentTypeResponseDTO> paymentTypeResponseDTOs = List.of(paymentTypeData.getPaymentTypeResponseDTO());

        List<PaymentType> paymentTypeModels = paymentTypeResponseMapper.dtosToModels(paymentTypeResponseDTOs);

        assertNotNull(paymentTypeModels);
    }

}