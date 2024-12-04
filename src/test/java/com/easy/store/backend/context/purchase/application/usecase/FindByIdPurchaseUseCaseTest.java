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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdPurchaseUseCaseTest {

    @InjectMocks
    private FindByIdPurchaseUseCase findByIdPurchaseUseCase;

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
    void findByIdSuccess() throws NoResultsException {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseResponseOne()));

        Purchase response = findByIdPurchaseUseCase.findById(purchaseData.getPurchaseResponseOne().getId());

        assertNotNull(response);
        assertEquals(response, purchaseData.getPurchaseResponseOne());

        verify(purchaseRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdPurchaseUseCase.findById(purchaseData.getPurchaseResponseOne().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(purchaseRepository).findById(any(Long.class));
    }

}