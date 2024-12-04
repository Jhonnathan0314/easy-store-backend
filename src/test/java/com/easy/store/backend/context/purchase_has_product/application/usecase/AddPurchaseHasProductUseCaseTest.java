package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.product.data.ProductData;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.data.PurchaseHasProductData;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
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
class AddPurchaseHasProductUseCaseTest {

    @InjectMocks
    private AddPurchaseHasProductUseCase addPurchaseHasProductUseCase;

    @Mock
    private PurchaseHasProductRepository purchaseHasProductRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    private static Long purchaseId;
    private static Long productId;
    private static PurchaseHasProductData purchaseHasProductData;
    private static PurchaseData purchaseData;
    private static ProductData productData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        purchaseId = 1L;
        productId = 1L;
        purchaseHasProductData = new PurchaseHasProductData();
        purchaseData = new PurchaseData();
        productData = new ProductData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void generateSuccess() throws InvalidBodyException, NoResultsException {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseResponseOne()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductResponseOne()));
        when(purchaseHasProductRepository.add(any(PurchaseHasProduct.class))).thenReturn(purchaseHasProductData.getPurchaseHasProductAddValid());

        PurchaseHasProduct response = addPurchaseHasProductUseCase.add(purchaseHasProductData.getPurchaseHasProductAddValid(), purchaseId, productId);

        assertNotNull(response);
        assertEquals(response, purchaseHasProductData.getPurchaseHasProductAddValid());

        verify(purchaseRepository).findById(any(Long.class));
        verify(productRepository).findById(any(Long.class));
        verify(purchaseHasProductRepository).add(any(PurchaseHasProduct.class));
    }

    @Test
    void generateFailedInvalidBodyException() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseEmpty()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductResponseOne()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> addPurchaseHasProductUseCase.add(purchaseHasProductData.getPurchaseHasProductAddInvalid(), purchaseId, productId)
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(purchaseHasProductRepository, never()).add(any(PurchaseHasProduct.class));
    }

    @Test
    void generateFailedNoResultsExceptionByUser() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> addPurchaseHasProductUseCase.add(purchaseHasProductData.getPurchaseHasProductResponseOne(), purchaseId, productId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_PURCHASE_RESULTS);

        verify(purchaseHasProductRepository, never()).add(any(PurchaseHasProduct.class));
    }

    @Test
    void generateFailedNoResultsExceptionByProduct() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseResponseOne()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> addPurchaseHasProductUseCase.add(purchaseHasProductData.getPurchaseHasProductResponseOne(), purchaseId, productId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_PRODUCT_RESULTS);

        verify(purchaseHasProductRepository, never()).add(any(PurchaseHasProduct.class));
    }
    
}