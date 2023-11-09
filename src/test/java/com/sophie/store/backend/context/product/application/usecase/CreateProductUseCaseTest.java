package com.sophie.store.backend.context.product.application.usecase;

import com.sophie.store.backend.context.product.data.ProductData;
import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
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
class CreateProductUseCaseTest {

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    private static Long subcategoryId;
    private static ProductData productData;
    private static SubcategoryData subcategoryData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        subcategoryId = 1L;
        productData = new ProductData();
        subcategoryData = new SubcategoryData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void createSuccess() throws InvalidBodyException, DuplicatedException, NoResultsException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(productRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(productRepository.create(any(Product.class))).thenReturn(productData.getProductCreateValid());

        Product response = createProductUseCase.create(productData.getProductCreateValid(), subcategoryId);

        assertNotNull(response);
        assertEquals(response, productData.getProductCreateValid());

        verify(productRepository).findByName(any(String.class));
        verify(productRepository).create(any(Product.class));
    }

    @Test
    void createFailedInvalidBodyException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createProductUseCase.create(productData.getProductCreateInvalid(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(productRepository, never()).findByName(any(String.class));
        verify(productRepository, never()).create(any(Product.class));
    }

    @Test
    void createFailedDuplicatedException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(productRepository.findByName(any(String.class))).thenReturn(Optional.of(productData.getProductActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createProductUseCase.create(productData.getProductActive(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(productRepository).findByName(any(String.class));
        verify(productRepository, never()).create(any(Product.class));
    }

    @Test
    void createFailedNoResultsException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> createProductUseCase.create(productData.getProductActive(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CATEGORY_RESULTS);

        verify(productRepository, never()).findByName(any(String.class));
        verify(productRepository, never()).create(any(Product.class));
    }
}