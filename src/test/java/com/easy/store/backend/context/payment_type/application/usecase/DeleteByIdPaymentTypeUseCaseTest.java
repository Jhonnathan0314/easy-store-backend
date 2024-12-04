package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteByIdPaymentTypeUseCaseTest {

    @InjectMocks
    private DeleteByIdPaymentTypeUseCase deleteByIdPaymentTypeUseCase;

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
    void deleteByIdSuccess() throws NonExistenceException {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));

        deleteByIdPaymentTypeUseCase.deleteById(paymentTypeData.getPaymentTypeActive().getId());

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdFailedNonExistenceException() throws NonExistenceException {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdPaymentTypeUseCase.deleteById(paymentTypeData.getPaymentTypeActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository, never()).deleteById(any(Long.class));
    }

}