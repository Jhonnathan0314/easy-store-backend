package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentTypeUseCaseTest {

    @InjectMocks
    private UpdatePaymentTypeUseCase updatePaymentTypeUseCase;

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
    void updateSuccess() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));
        when(paymentTypeRepository.update(any(PaymentType.class))).thenReturn(paymentTypeData.getPaymentTypeUpdated());

        PaymentType response = updatePaymentTypeUseCase.update(paymentTypeData.getPaymentTypeToUpdate());

        assertNotNull(response);
        assertEquals(response, paymentTypeData.getPaymentTypeUpdated());

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository).update(any(PaymentType.class));
    }

    @Test
    void updateFailedNoIdReceivedException() {
        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updatePaymentTypeUseCase.update(paymentTypeData.getPaymentTypeToUpdateNoId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(paymentTypeRepository, never()).findById(any(Long.class));
        verify(paymentTypeRepository, never()).update(any(PaymentType.class));
    }

    @Test
    void updateFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updatePaymentTypeUseCase.update(paymentTypeData.getPaymentTypeToUpdateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(paymentTypeRepository, never()).findById(any(Long.class));
        verify(paymentTypeRepository, never()).update(any(PaymentType.class));
    }

    @Test
    void updateFailedNoResultsException() {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updatePaymentTypeUseCase.update(paymentTypeData.getPaymentTypeToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository, never()).update(any(PaymentType.class));
    }

    @Test
    void updateFailedNoChangesException() {
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updatePaymentTypeUseCase.update(paymentTypeData.getPaymentTypeActive())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(paymentTypeRepository).findById(any(Long.class));
        verify(paymentTypeRepository, never()).update(any(PaymentType.class));
    }

}