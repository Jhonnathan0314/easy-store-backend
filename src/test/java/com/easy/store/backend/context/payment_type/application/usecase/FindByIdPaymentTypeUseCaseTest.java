package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdPaymentTypeUseCaseTest {

    @InjectMocks
    private FindByIdPaymentTypeUseCase findByIdPaymentTypeUseCase;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    private static ErrorMessages errorMessages;
    private static PaymentTypeData paymentTypeData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        paymentTypeData = new PaymentTypeData();
    }

    @Test
    void findByIdSuccess() throws NoResultsException {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));

        PaymentType response = findByIdPaymentTypeUseCase.findById(paymentTypeData.getPaymentTypeActive().getId());

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeActive());

        verify(paymentTypeRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdPaymentTypeUseCase.findById(paymentTypeData.getPaymentTypeActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(paymentTypeRepository).findById(any(Long.class));
    }

}