package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
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
class ChangeStateByIdProductUseCaseTest {

    @InjectMocks
    private ChangeStateByIdProductUseCase changeStateByIdProductUseCase;

    @Mock
    private ProductRepository productRepository;

    private static ProductData productData;
    private static ErrorMessages errorMessages;
    private static Long productId;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
        errorMessages = new ErrorMessages();
        productId = 1L;
    }

    @Test
    void successInactivePerson() throws NonExistenceException {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductActive()));
        when(productRepository.update(any(Product.class))).thenReturn(productData.getProductInactive());

        Product response = changeStateByIdProductUseCase.changeStateById(productData.getProductActive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), productData.getProductInactive().getState());

        verify(productRepository).findById(any(Long.class));
        verify(productRepository).update(any(Product.class));
    }

    @Test
    void successActivePerson() throws NonExistenceException {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductInactive()));
        when(productRepository.update(any(Product.class))).thenReturn(productData.getProductActive());

        Product response = changeStateByIdProductUseCase.changeStateById(productData.getProductInactive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), productData.getProductActive().getState());

        verify(productRepository).findById(any(Long.class));
        verify(productRepository).update(any(Product.class));
    }

    @Test
    void failedChangeStateByIdNonExistenceException() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> changeStateByIdProductUseCase.changeStateById(productId)
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(productRepository).findById(productId);
        verify(productRepository, never()).update(any(Product.class));
    }
}