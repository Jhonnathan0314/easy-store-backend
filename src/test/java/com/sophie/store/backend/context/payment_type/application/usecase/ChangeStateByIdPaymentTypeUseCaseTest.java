package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.data.PaymentTypeData;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeStateByIdPaymentTypeUseCaseTest {

    @InjectMocks
    private ChangeStateByIdPaymentTypeUseCase changeStateByIdPaymentTypeUseCase;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    private static PaymentTypeData paymentTypeData;
    private static ErrorMessages errorMessages;
    private static Long paymentTypeId;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
        errorMessages = new ErrorMessages();
        paymentTypeId = 1L;
    }

    @Test
    void successInactivePerson() throws NonExistenceException {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));
        when(paymentTypeRepository.update(any(PaymentType.class))).thenReturn(paymentTypeData.getPaymentTypeInactive());

        PaymentType response = changeStateByIdPaymentTypeUseCase.changeStateById(paymentTypeData.getPaymentTypeActive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), paymentTypeData.getPaymentTypeInactive().getState());

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository).update(any(PaymentType.class));
    }

    @Test
    void successActivePerson() throws NonExistenceException {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeInactive()));
        when(paymentTypeRepository.update(any(PaymentType.class))).thenReturn(paymentTypeData.getPaymentTypeActive());

        PaymentType response = changeStateByIdPaymentTypeUseCase.changeStateById(paymentTypeData.getPaymentTypeInactive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), paymentTypeData.getPaymentTypeActive().getState());

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository).update(any(PaymentType.class));
    }

    @Test
    void failedChangeStateByIdNonExistenceException() {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> changeStateByIdPaymentTypeUseCase.changeStateById(paymentTypeId)
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(paymentTypeRepository).findById(paymentTypeId);
        verify(paymentTypeRepository, never()).update(any(PaymentType.class));
    }
}