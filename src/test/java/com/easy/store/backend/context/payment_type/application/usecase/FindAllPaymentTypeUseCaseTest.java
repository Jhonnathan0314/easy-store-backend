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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllPaymentTypeUseCaseTest {

    @InjectMocks
    private FindAllPaymentTypeUseCase findAllPaymentTypeUseCase;

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
    void findAllPaymentTypeSuccess() throws NoResultsException {
        when(paymentTypeRepository.findAll()).thenReturn(paymentTypeData.getPaymentTypesList());

        List<PaymentType> response = findAllPaymentTypeUseCase.findAll();

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllPaymentTypeFailedNoResultsException() {
        when(paymentTypeRepository.findAll()).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllPaymentTypeUseCase.findAll()
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(paymentTypeRepository).findAll();
    }

}