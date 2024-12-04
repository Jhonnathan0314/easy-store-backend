package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.data.ProductData;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByNameProductUseCaseTest {

    @InjectMocks
    private FindByNameProductUseCase findByNameProductUseCase;

    @Mock
    private ProductRepository productRepository;

    private static ProductData productData;

    @BeforeAll
    static void setUp() {
        productData = new ProductData();
    }

    @Test
    void findByNameSuccess() {
        when(productRepository.findByName(any(String.class))).thenReturn(Optional.of(productData.getProductActive()));

        Product response = findByNameProductUseCase.findByName(productData.getProductActive().getName()).orElse(null);

        assertNotNull(response);
        assertEquals(response, productData.getProductActive());

        verify(productRepository).findByName(any(String.class));
    }

}