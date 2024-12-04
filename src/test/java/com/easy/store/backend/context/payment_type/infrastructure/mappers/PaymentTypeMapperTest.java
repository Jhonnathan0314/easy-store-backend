package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentTypeMapperTest {

    @InjectMocks
    private PaymentTypeMapper paymentTypeMapper;

    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
    }

    @Test
    void entityToModelTest() {
        PaymentTypeEntity paymentTypeEntity = paymentTypeData.getPaymentTypeEntity();

        PaymentType paymentTypeModel = paymentTypeMapper.entityToModel(paymentTypeEntity);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void modelToEntityTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeEntity paymentTypeEntity = paymentTypeMapper.modelToEntity(paymentTypeModel);

        assertNotNull(paymentTypeEntity);
    }

    @Test
    void modelToDtoTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.modelToDto(paymentTypeModel);

        assertNotNull(paymentTypeDTO);
    }

    @Test
    void dtoToModelTest() {
        PaymentTypeDTO paymentTypeDTO = paymentTypeData.getPaymentTypeDTO();

        PaymentType paymentTypeModel = paymentTypeMapper.dtoToModel(paymentTypeDTO);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PaymentTypeEntity> paymentTypeEntities = List.of(paymentTypeData.getPaymentTypeEntity());

        List<PaymentType> paymentTypeModels = paymentTypeMapper.entitiesToModels(paymentTypeEntities);

        assertNotNull(paymentTypeModels);
        assertEquals(paymentTypeEntities.size(), paymentTypeModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeMapper.modelsToEntities(paymentTypeModels);

        assertNotNull(paymentTypeEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeDTO> paymentTypeDTOs = paymentTypeMapper.modelsToDtos(paymentTypeModels);

        assertNotNull(paymentTypeDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PaymentTypeDTO> paymentTypeDTOs = List.of(paymentTypeData.getPaymentTypeDTO());

        List<PaymentType> paymentTypeModels = paymentTypeMapper.dtosToModels(paymentTypeDTOs);

        assertNotNull(paymentTypeModels);
    }

}