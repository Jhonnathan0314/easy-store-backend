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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AddAllPurchaseHasProductUseCaseTest {

    @InjectMocks
    private AddAllPurchaseHasProductUseCase addAllPurchaseHasProductUseCase;

    @Mock
    private PurchaseHasProductRepository purchaseHasProductRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    private static Long purchaseId;
    private static PurchaseHasProductData purchaseHasProductData;
    private static PurchaseData purchaseData;
    private static ProductData productData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        purchaseId = 1L;
        purchaseHasProductData = new PurchaseHasProductData();
        purchaseData = new PurchaseData();
        productData = new ProductData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void addAllSuccess() throws InvalidBodyException, NoResultsException {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseResponseOne()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductResponseOne()));
        when(purchaseHasProductRepository.addAll(any())).thenReturn(purchaseHasProductData.getPurchaseHasProductsList());

        List<PurchaseHasProduct> response = addAllPurchaseHasProductUseCase.addAll(purchaseHasProductData.getPurchaseHasProductsList(), purchaseId, purchaseHasProductData.getProductsId());

        assertNotNull(response);
        assertEquals(response.size(), purchaseHasProductData.getPurchaseHasProductsList().size());

        verify(purchaseRepository, times(2)).findById(any(Long.class));
        verify(productRepository, times(2)).findById(any(Long.class));
        verify(purchaseHasProductRepository).addAll(any());
    }

    @Test
    void addAllFailedInvalidBodyException() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseEmpty()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductResponseOne()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> addAllPurchaseHasProductUseCase.addAll(purchaseHasProductData.getPurchaseHasProductsListInvalid(), purchaseId, purchaseHasProductData.getProductsId())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(purchaseHasProductRepository, never()).addAll(any());
    }

    @Test
    void addAllFailedInvalidBodyExceptionDifferentSize() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> addAllPurchaseHasProductUseCase.addAll(purchaseHasProductData.getPurchaseHasProductsEmptyList(), purchaseId, purchaseHasProductData.getProductsId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_SIZE_EQUALS);

        verify(purchaseHasProductRepository, never()).addAll(any());
    }

    @Test
    void addAllFailedNoResultsExceptionByUser() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> addAllPurchaseHasProductUseCase.addAll(purchaseHasProductData.getPurchaseHasProductsList(), purchaseId, purchaseHasProductData.getProductsId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_PURCHASE_RESULTS);

        verify(purchaseHasProductRepository, never()).add(any(PurchaseHasProduct.class));
    }

    @Test
    void addAllFailedNoResultsExceptionByProduct() {
        when(purchaseRepository.findById(any(Long.class))).thenReturn(Optional.of(purchaseData.getPurchaseResponseOne()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> addAllPurchaseHasProductUseCase.addAll(purchaseHasProductData.getPurchaseHasProductsList(), purchaseId, purchaseHasProductData.getProductsId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_PRODUCT_RESULTS);

        verify(purchaseHasProductRepository, never()).add(any(PurchaseHasProduct.class));
    }

}