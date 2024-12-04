package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByNamePaymentTypeUseCaseTest {

    @InjectMocks
    private FindByNamePaymentTypeUseCase findByNamePaymentTypeUseCase;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
    }

    @Test
    void findByNameSuccess() {
        when(paymentTypeRepository.findByName(any(String.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));

        PaymentType response = findByNamePaymentTypeUseCase.findByName(paymentTypeData.getPaymentTypeActive().getName()).orElse(null);

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeActive());

        verify(paymentTypeRepository).findByName(any(String.class));
    }

}