package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.data.ProductData;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
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
class FindByIdProductUseCaseTest {

    @InjectMocks
    private FindByIdProductUseCase findByIdProductUseCase;

    @Mock
    private ProductRepository productRepository;

    private static ErrorMessages errorMessages;
    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        productData = new ProductData();
    }

    @Test
    void findByIdSuccess() throws NoResultsException {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductActive()));

        Product response = findByIdProductUseCase.findById(productData.getProductActive().getId());

        assertNotNull(response);
        assertEquals(response, productData.getProductActive());

        verify(productRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdProductUseCase.findById(productData.getProductActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(productRepository).findById(any(Long.class));
    }

}