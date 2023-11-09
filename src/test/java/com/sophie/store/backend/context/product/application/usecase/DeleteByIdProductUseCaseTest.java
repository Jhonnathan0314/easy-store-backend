package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.data.ProductData;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteByIdProductUseCaseTest {

    @InjectMocks
    private DeleteByIdProductUseCase deleteByIdProductUseCase;

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
    void deleteByIdSuccess() throws NonExistenceException {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductActive()));

        deleteByIdProductUseCase.deleteById(productData.getProductActive().getId());

        verify(productRepository).findById(any(Long.class));
        verify(productRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdFailedNonExistenceException() throws NonExistenceException {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdProductUseCase.deleteById(productData.getProductActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(productRepository).findById(any(Long.class));
        verify(productRepository, never()).deleteById(any(Long.class));
    }

}