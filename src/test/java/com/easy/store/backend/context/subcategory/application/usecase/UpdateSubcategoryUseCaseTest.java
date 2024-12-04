package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.category.data.CategoryData;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
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
class UpdateSubcategoryUseCaseTest {

    @InjectMocks
    private UpdateSubcategoryUseCase updateSubcategoryUseCase;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private static Long categoryId;
    private static ErrorMessages errorMessages;
    private static SubcategoryData subcategoryData;
    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryId = 1L;
        errorMessages = new ErrorMessages();
        subcategoryData = new SubcategoryData();
        categoryData = new CategoryData();
    }

    @Test
    void updateSuccess() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(subcategoryRepository.update(any(Subcategory.class))).thenReturn(subcategoryData.getSubcategoryUpdated());

        Subcategory response = updateSubcategoryUseCase.update(subcategoryData.getSubcategoryToUpdate(), categoryId);

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryUpdated());

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository).update(any(Subcategory.class));
    }

    @Test
    void updateFailedNoIdReceivedException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateSubcategoryUseCase.update(subcategoryData.getSubcategoryToUpdateNoId(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(subcategoryRepository, never()).findById(any(Long.class));
        verify(subcategoryRepository, never()).update(any(Subcategory.class));
    }

    @Test
    void updateFailedInvalidBodyException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateSubcategoryUseCase.update(subcategoryData.getSubcategoryToUpdateInvalid(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(subcategoryRepository, never()).findById(any(Long.class));
        verify(subcategoryRepository, never()).update(any(Subcategory.class));
    }

    @Test
    void updateFailedNoResultsException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateSubcategoryUseCase.update(subcategoryData.getSubcategoryToUpdate(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository, never()).update(any(Subcategory.class));
    }

    @Test
    void updateFailedNoResultsExceptionByCategory() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateSubcategoryUseCase.update(subcategoryData.getSubcategoryToUpdate(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CATEGORY_RESULTS);

        verify(subcategoryRepository, never()).findById(any(Long.class));
        verify(subcategoryRepository, never()).update(any(Subcategory.class));
    }

    @Test
    void updateFailedNoChangesException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateSubcategoryUseCase.update(subcategoryData.getSubcategoryActive(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository, never()).update(any(Subcategory.class));
    }

}