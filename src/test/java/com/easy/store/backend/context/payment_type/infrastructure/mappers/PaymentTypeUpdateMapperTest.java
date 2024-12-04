package com.easy.store.backend.context.payment_type.infrastructure.mappers;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
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
class PaymentTypeUpdateMapperTest {

    @InjectMocks
    private PaymentTypeUpdateMapper paymentTypeUpdateMapper;

    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
    }

    @Test
    void entityToModelTest() {
        PaymentTypeEntity paymentTypeEntity = paymentTypeData.getPaymentTypeEntity();

        PaymentType paymentTypeModel = paymentTypeUpdateMapper.entityToModel(paymentTypeEntity);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void modelToEntityTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeEntity paymentTypeEntity = paymentTypeUpdateMapper.modelToEntity(paymentTypeModel);

        assertNotNull(paymentTypeEntity);
    }

    @Test
    void modelToDtoTest() {
        PaymentType paymentTypeModel = paymentTypeData.getPaymentTypeModel();

        PaymentTypeUpdateDTO paymentTypeUpdateDTO = paymentTypeUpdateMapper.modelToDto(paymentTypeModel);

        assertNotNull(paymentTypeUpdateDTO);
    }

    @Test
    void dtoToModelTest() {
        PaymentTypeUpdateDTO paymentTypeUpdateDTO = paymentTypeData.getPaymentTypeUpdateDTO();

        PaymentType paymentTypeModel = paymentTypeUpdateMapper.dtoToModel(paymentTypeUpdateDTO);

        assertNotNull(paymentTypeModel);
    }

    @Test
    void entitiesToModelsTest() {
        List<PaymentTypeEntity> paymentTypeEntities = List.of(paymentTypeData.getPaymentTypeEntity());

        List<PaymentType> paymentTypeModels = paymentTypeUpdateMapper.entitiesToModels(paymentTypeEntities);

        assertNotNull(paymentTypeModels);
        assertEquals(paymentTypeEntities.size(), paymentTypeModels.size());
    }

    @Test
    void modelsToEntitiesTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeUpdateMapper.modelsToEntities(paymentTypeModels);

        assertNotNull(paymentTypeEntities);
    }

    @Test
    void modelsToDtosTest() {
        List<PaymentType> paymentTypeModels = List.of(paymentTypeData.getPaymentTypeModel());

        List<PaymentTypeUpdateDTO> paymentTypeUpdateDTOs = paymentTypeUpdateMapper.modelsToDtos(paymentTypeModels);

        assertNotNull(paymentTypeUpdateDTOs);
    }

    @Test
    void dtosToModelsTest() {
        List<PaymentTypeUpdateDTO> paymentTypeUpdateDTOs = List.of(paymentTypeData.getPaymentTypeUpdateDTO());

        List<PaymentType> paymentTypeModels = paymentTypeUpdateMapper.dtosToModels(paymentTypeUpdateDTOs);

        assertNotNull(paymentTypeModels);
    }

}