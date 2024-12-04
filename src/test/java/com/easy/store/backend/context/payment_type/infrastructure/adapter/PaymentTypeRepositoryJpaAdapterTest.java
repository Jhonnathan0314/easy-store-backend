package com.easy.store.backend.context.payment_type.infrastructure.adapter;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTypeRepositoryJpaAdapterTest {

    @InjectMocks
    private PaymentTypeRepositoryJpaAdapter paymentTypeRepositoryJpaAdapter;


    @Mock
    private PaymentTypeJpaRepository paymentTypeJpaRepository;

    private static PaymentTypeMapper paymentTypeMapper;
    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
        paymentTypeMapper = new PaymentTypeMapper();
    }

    @Test
    @Order(0)
    void findAllTest() {
        List<PaymentTypeEntity> mockEntities = paymentTypeMapper.modelsToEntities(paymentTypeData.getPaymentTypesList());
        when(paymentTypeJpaRepository.findAll()).thenReturn(mockEntities);

        List<PaymentType> response = paymentTypeRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(paymentTypeJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdTest() {
        PaymentTypeEntity mockEntity = paymentTypeMapper.modelToEntity(paymentTypeData.getPaymentTypeResponseOne());
        when(paymentTypeJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        PaymentType response = paymentTypeRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeResponseOne());

        verify(paymentTypeJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByNameTest() {
        PaymentTypeEntity mockEntity = paymentTypeMapper.modelToEntity(paymentTypeData.getPaymentTypeResponseOne());
        when(paymentTypeJpaRepository.findByName(any(String.class))).thenReturn(Optional.of(mockEntity));

        PaymentType response = paymentTypeRepositoryJpaAdapter.findByName("test").orElse(null);

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeResponseOne());

        verify(paymentTypeJpaRepository).findByName(any(String.class));
    }

    @Test
    @Order(3)
    void createTest() {
        PaymentTypeEntity mockEntity = paymentTypeMapper.modelToEntity(paymentTypeData.getPaymentTypeResponseOne());
        when(paymentTypeJpaRepository.save(any(PaymentTypeEntity.class))).thenReturn(mockEntity);

        PaymentType response = paymentTypeRepositoryJpaAdapter.create(paymentTypeData.getPaymentTypeCreateValid());

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeResponseOne());

        verify(paymentTypeJpaRepository).save(any(PaymentTypeEntity.class));
    }

    @Test
    @Order(4)
    void updateTest() {
        PaymentTypeEntity mockEntity = paymentTypeMapper.modelToEntity(paymentTypeData.getPaymentTypeResponseOne());
        when(paymentTypeJpaRepository.save(any(PaymentTypeEntity.class))).thenReturn(mockEntity);

        PaymentType response = paymentTypeRepositoryJpaAdapter.update(paymentTypeData.getPaymentTypeActive());

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeResponseOne());

        verify(paymentTypeJpaRepository).save(any(PaymentTypeEntity.class));
    }

    @Test
    @Order(5)
    void deleteByIdTest() {
        paymentTypeRepositoryJpaAdapter.deleteById(1L);

        verify(paymentTypeJpaRepository).deleteById(any(Long.class));
    }
}