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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllProductUseCaseTest {

    @InjectMocks
    private FindAllProductUseCase findAllProductUseCase;

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
    void findAllProductSuccess() throws NoResultsException {
        when(productRepository.findAll()).thenReturn(productData.getProductsList());

        List<Product> response = findAllProductUseCase.findAll();

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllProductFailedNoResultsException() {
        when(productRepository.findAll()).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllProductUseCase.findAll()
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(productRepository).findAll();
    }

}