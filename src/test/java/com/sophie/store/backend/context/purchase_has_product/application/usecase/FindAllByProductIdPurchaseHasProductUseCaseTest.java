package com.sophie.store.backend.context.purchase_has_product.application.usecase;

import com.sophie.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
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
class FindAllByProductIdPurchaseHasProductUseCaseTest {

    @InjectMocks
    private FindAllByProductIdPurchaseHasProductUseCase findAllByProductIdPurchaseHasProductUseCase;

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
    void findAllPurchaseHasProductSuccess() throws NoResultsException {
        when(purchaseHasProductRepository.findAllByProductId(any(Long.class))).thenReturn(purchaseHasProductData.getPurchaseHasProductsList());

        List<PurchaseHasProduct> response = findAllByProductIdPurchaseHasProductUseCase.findAllByProductId(purchaseHasProductData.getPurchaseHasProductResponseOne().getProduct().getId());

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllPurchaseHasProductFailedNoResultsException() {
        when(purchaseHasProductRepository.findAllByProductId(any(Long.class))).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllByProductIdPurchaseHasProductUseCase.findAllByProductId(purchaseHasProductData.getPurchaseHasProductResponseOne().getProduct().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(purchaseHasProductRepository).findAllByProductId(any(Long.class));
    }

}