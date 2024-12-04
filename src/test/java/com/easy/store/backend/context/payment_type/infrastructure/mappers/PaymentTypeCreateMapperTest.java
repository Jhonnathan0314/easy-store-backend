package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
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
class PaymentTypeCreateMapperTest {

    @InjectMocks
    private PaymentTypeCreateMapper paymentTypeCreateMapper;

    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
    }

    @Test
    void entityToModelTest() {
        PaymentTypeEntity paymentTypeEntity = paymentTypeData.getPaymentTypeEntity();

        PaymentType paymentTypeModel = paymentTypeCreateMapper.entityToModel(paymentTypeEntity);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void modelToEntityTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeEntity paymentTypeEntity = paymentTypeCreateMapper.modelToEntity(paymentTypeModel);

        assertNotNull(paymentTypeEntity);
    }

    @Test
    void modelToDtoTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeCreateDTO paymentTypeCreateDTO = paymentTypeCreateMapper.modelToDto(paymentTypeModel);

        assertNotNull(paymentTypeCreateDTO);
    }

    @Test
    void dtoToModelTest() {
        PaymentTypeCreateDTO paymentTypeCreateDTO = paymentTypeData.getPaymentTypeCreateDTO();

        PaymentType paymentTypeModel = paymentTypeCreateMapper.dtoToModel(paymentTypeCreateDTO);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PaymentTypeEntity> paymentTypeEntities = List.of(paymentTypeData.getPaymentTypeEntity());

        List<PaymentType> paymentTypeModels = paymentTypeCreateMapper.entitiesToModels(paymentTypeEntities);

        assertNotNull(paymentTypeModels);
        assertEquals(paymentTypeEntities.size(), paymentTypeModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeCreateMapper.modelsToEntities(paymentTypeModels);

        assertNotNull(paymentTypeEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeCreateDTO> paymentTypeCreateDTOs = paymentTypeCreateMapper.modelsToDtos(paymentTypeModels);

        assertNotNull(paymentTypeCreateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PaymentTypeCreateDTO> paymentTypeCreateDTOs = List.of(paymentTypeData.getPaymentTypeCreateDTO());

        List<PaymentType> paymentTypeModels = paymentTypeCreateMapper.dtosToModels(paymentTypeCreateDTOs);

        assertNotNull(paymentTypeModels);
    }

}