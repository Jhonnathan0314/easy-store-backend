package com.sophie.store.backend.context.purchase.application.usecase;

import com.sophie.store.backend.context.purchase.data.PurchaseData;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByPaymentTypeIdPurchaseUseCaseTest {

    @InjectMocks
    private FindByPaymentTypeIdPurchaseUseCase findByPaymentTypeIdPurchaseUseCase;

    @Mock
    private PurchaseRepository purchaseRepository;

    private static ErrorMessages errorMessages;
    private static PurchaseData purchaseData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        purchaseData = new PurchaseData();
    }

    @Test
    void findByPaymentTypeIdSuccess() throws NoResultsException {
        when(purchaseRepository.findByPaymentTypeId(any(Long.class))).thenReturn(purchaseData.getPurchasesList());

        List<Purchase> response = findByPaymentTypeIdPurchaseUseCase.findByPaymentTypeId(purchaseData.getPurchaseResponseOne().getPaymentType().getId());

        assertNotNull(response);
        assertEquals(response.size(), purchaseData.getPurchasesList().size());

        verify(purchaseRepository).findByPaymentTypeId(any(Long.class));
    }

    @Test
    void findByPaymentTypeIdFailedNoResultsException() {
        when(purchaseRepository.findByPaymentTypeId(any(Long.class))).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByPaymentTypeIdPurchaseUseCase.findByPaymentTypeId(purchaseData.getPurchaseResponseOne().getPaymentType().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(purchaseRepository).findByPaymentTypeId(any(Long.class));
    }

}