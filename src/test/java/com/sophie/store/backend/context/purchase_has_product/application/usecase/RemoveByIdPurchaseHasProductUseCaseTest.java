package com.sophie.store.backend.context.purchase_has_product.application.usecase;

import com.sophie.store.backend.context.purchase_has_product.application.usecase.RemoveByIdPurchaseHasProductUseCase;
import com.sophie.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.sophie.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
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
class RemoveByIdPurchaseHasProductUseCaseTest {

    @InjectMocks
    private RemoveByIdPurchaseHasProductUseCase removeByIdPurchaseHasProductUseCase;

    @Mock
    private PurchaseHasProductRepository purchaseHasProductRepository;

    private static ErrorMessages errorMessages;
    private static PurchaseHasProductData purchaseHasProductData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        purchaseHasProductData = new PurchaseHasProductData();
    }

    @Test
    void removeByIdSuccess() throws NonExistenceException {
        when(purchaseHasProductRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseHasProductData.getPurchaseHasProductResponseOne()));

        removeByIdPurchaseHasProductUseCase.removeById(purchaseHasProductData.getPurchaseHasProductResponseOne().getId());

        verify(purchaseHasProductRepository).findById(any(Long.class));
        verify(purchaseHasProductRepository).removeById(any(Long.class));
    }

    @Test
    void removeByIdFailedNonExistenceException() throws NonExistenceException {
        when(purchaseHasProductRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> removeByIdPurchaseHasProductUseCase.removeById(purchaseHasProductData.getPurchaseHasProductResponseOne().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(purchaseHasProductRepository).findById(any(Long.class));
        verify(purchaseHasProductRepository, never()).removeById(any(Long.class));
    }
    
}