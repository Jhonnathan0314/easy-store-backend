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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByTotalBetweenPurchaseUseCaseTest {

    @InjectMocks
    private FindByTotalBetweenPurchaseUseCase findByTotalBetweenPurchaseUseCase;

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
    void FindByDateTotalBetweenPurchaseSuccess() throws NoResultsException {
        when(purchaseRepository.findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(purchaseData.getPurchasesList());

        List<Purchase> response = findByTotalBetweenPurchaseUseCase.findByTotalBetween(purchaseData.getFromTotal(), purchaseData.getToTotal());

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void FindByDateTotalPurchaseFailedNoResultsException() {
        when(purchaseRepository.findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findByTotalBetweenPurchaseUseCase.findByTotalBetween(purchaseData.getFromTotal(), purchaseData.getToTotal())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(purchaseRepository).findByTotalBetween(any(BigDecimal.class), any(BigDecimal.class));
    }

}