package com.sophie.store.backend.context.payment_type.application.usecase;

import com.sophie.store.backend.context.payment_type.data.PaymentTypeData;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
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
class CreatePaymentTypeUseCaseTest {

    @InjectMocks
    private CreatePaymentTypeUseCase createPaymentTypeUseCase;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    private static PaymentTypeData paymentTypeData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        paymentTypeData = new PaymentTypeData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void createSuccess() throws InvalidBodyException, DuplicatedException {
        when(paymentTypeRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(paymentTypeRepository.create(any(PaymentType.class))).thenReturn(paymentTypeData.getPaymentTypeCreateValid());

        PaymentType response = createPaymentTypeUseCase.create(paymentTypeData.getPaymentTypeCreateValid());

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeCreateValid());

        verify(paymentTypeRepository).findByName(any(String.class));
        verify(paymentTypeRepository).create(any(PaymentType.class));
    }

    @Test
    void createFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createPaymentTypeUseCase.create(paymentTypeData.getPaymentTypeCreateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(paymentTypeRepository, never()).findByName(any(String.class));
        verify(paymentTypeRepository, never()).create(any(PaymentType.class));
    }

    @Test
    void createFailedDuplicatedException() {
        when(paymentTypeRepository.findByName(any(String.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createPaymentTypeUseCase.create(paymentTypeData.getPaymentTypeActive())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(paymentTypeRepository).findByName(any(String.class));
        verify(paymentTypeRepository, never()).create(any(PaymentType.class));
    }
}