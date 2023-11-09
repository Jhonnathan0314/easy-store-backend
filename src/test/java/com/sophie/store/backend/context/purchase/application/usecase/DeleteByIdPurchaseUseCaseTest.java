package com.sophie.store.backend.context.purchase.application.usecase;

import com.sophie.store.backend.context.purchase.data.PurchaseData;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
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
class DeleteByIdPurchaseUseCaseTest {

    @InjectMocks
    private DeleteByIdPurchaseUseCase deleteByIdPurchaseUseCase;

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
    void deleteByIdSuccess() throws NonExistenceException {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseResponseOne()));

        deleteByIdPurchaseUseCase.deleteById(purchaseData.getPurchaseResponseOne().getId());

        verify(purchaseRepository).findById(any(Long.class));
        verify(purchaseRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdFailedNonExistenceException() throws NonExistenceException {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdPurchaseUseCase.deleteById(purchaseData.getPurchaseResponseOne().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(purchaseRepository).findById(any(Long.class));
        verify(purchaseRepository, never()).deleteById(any(Long.class));
    }

}