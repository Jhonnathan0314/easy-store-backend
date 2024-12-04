package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
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
class FindByIdPurchaseHasProductUseCaseTest {

    @InjectMocks
    private FindByIdPurchaseHasProductUseCase findByIdPurchaseHasProductUseCase;

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
    void findByIdSuccess() throws NoResultsException {
        when(purchaseHasProductRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseHasProductData.getPurchaseHasProductResponseOne()));

        PurchaseHasProduct response = findByIdPurchaseHasProductUseCase.findById(purchaseHasProductData.getPurchaseHasProductResponseOne().getId());

        assertNotNull(response);
        assertEquals(response, purchaseHasProductData.getPurchaseHasProductResponseOne());

        verify(purchaseHasProductRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(purchaseHasProductRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdPurchaseHasProductUseCase.findById(purchaseHasProductData.getPurchaseHasProductResponseOne().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(purchaseHasProductRepository).findById(any(Long.class));
    }

}