package com.easy.store.backend.context.product.application.usecase;

import com.easy.store.backend.context.product.data.ProductData;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
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
class UpdateProductUseCaseTest {

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    private static Long subcategoryId;
    private static ErrorMessages errorMessages;
    private static ProductData productData;
    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryId = 1L;
        errorMessages = new ErrorMessages();
        productData = new ProductData();
        subcategoryData = new SubcategoryData();
    }

    @Test
    void updateSuccess() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductActive()));
        when(productRepository.update(any(Product.class))).thenReturn(productData.getProductUpdated());

        Product response = updateProductUseCase.update(productData.getProductToUpdate(), subcategoryId);

        assertNotNull(response);
        assertEquals(response, productData.getProductUpdated());

        verify(productRepository).findById(any(Long.class));
        verify(productRepository).update(any(Product.class));
    }

    @Test
    void updateFailedNoIdReceivedException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateProductUseCase.update(productData.getProductToUpdateNoId(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(productRepository, never()).findById(any(Long.class));
        verify(productRepository, never()).update(any(Product.class));
    }

    @Test
    void updateFailedInvalidBodyException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateProductUseCase.update(productData.getProductToUpdateInvalid(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(productRepository, never()).findById(any(Long.class));
        verify(productRepository, never()).update(any(Product.class));
    }

    @Test
    void updateFailedNoResultsException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateProductUseCase.update(productData.getProductToUpdate(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(productRepository).findById(any(Long.class));
        verify(productRepository, never()).update(any(Product.class));
    }

    @Test
    void updateFailedNoResultsExceptionBySubcategory() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateProductUseCase.update(productData.getProductToUpdate(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CATEGORY_RESULTS);

        verify(productRepository, never()).findById(any(Long.class));
        verify(productRepository, never()).update(any(Product.class));
    }

    @Test
    void updateFailedNoChangesException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(productData.getProductActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateProductUseCase.update(productData.getProductActive(), subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(productRepository).findById(any(Long.class));
        verify(productRepository, never()).update(any(Product.class));
    }

}