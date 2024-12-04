package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByDateBetweenPurchaseUseCaseTest {

    @InjectMocks
    private FindByDateBetweenPurchaseUseCase findByDateBetweenPurchaseUseCase;

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
    void FindByDateBetweenPurchaseSuccess() throws NoResultsException {
        when(purchaseRepository.findByDateBetween(any(Timestamp.class), any(Timestamp.class))).thenReturn(purchaseData.getPurchasesList());

        List<Purchase> response = findByDateBetweenPurchaseUseCase.findByDateBetween(purchaseData.getFromDate(), purchaseData.getToDate());

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void FindByDateBetweenPurchaseFailedNoResultsException() {
        when(purchaseRepository.findByDateBetween(any(Timestamp.class), any(Timestamp.class))).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findByDateBetweenPurchaseUseCase.findByDateBetween(purchaseData.getFromDate(), purchaseData.getToDate())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(purchaseRepository).findByDateBetween(any(Timestamp.class), any(Timestamp.class));
    }

}