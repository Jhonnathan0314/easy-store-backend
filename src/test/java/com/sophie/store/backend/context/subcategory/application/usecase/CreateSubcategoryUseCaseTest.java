package com.sophie.store.backend.context.subcategory.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
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
class CreateSubcategoryUseCaseTest {

    @InjectMocks
    private CreateSubcategoryUseCase createSubcategoryUseCase;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private static Long categoryId;
    private static SubcategoryData subcategoryData;
    private static CategoryData categoryData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        categoryId = 1L;
        subcategoryData = new SubcategoryData();
        categoryData = new CategoryData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void createSuccess() throws InvalidBodyException, DuplicatedException, NoResultsException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(subcategoryRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(subcategoryRepository.create(any(Subcategory.class))).thenReturn(subcategoryData.getSubcategoryCreateValid());

        Subcategory response = createSubcategoryUseCase.create(subcategoryData.getSubcategoryCreateValid(), categoryId);

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryCreateValid());

        verify(subcategoryRepository).findByName(any(String.class));
        verify(subcategoryRepository).create(any(Subcategory.class));
    }

    @Test
    void createFailedInvalidBodyException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createSubcategoryUseCase.create(subcategoryData.getSubcategoryCreateInvalid(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(subcategoryRepository, never()).findByName(any(String.class));
        verify(subcategoryRepository, never()).create(any(Subcategory.class));
    }

    @Test
    void createFailedDuplicatedException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(subcategoryRepository.findByName(any(String.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createSubcategoryUseCase.create(subcategoryData.getSubcategoryActive(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(subcategoryRepository).findByName(any(String.class));
        verify(subcategoryRepository, never()).create(any(Subcategory.class));
    }

    @Test
    void createFailedNoResultsException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> createSubcategoryUseCase.create(subcategoryData.getSubcategoryActive(), categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CATEGORY_RESULTS);

        verify(subcategoryRepository, never()).findByName(any(String.class));
        verify(subcategoryRepository, never()).create(any(Subcategory.class));
    }
}