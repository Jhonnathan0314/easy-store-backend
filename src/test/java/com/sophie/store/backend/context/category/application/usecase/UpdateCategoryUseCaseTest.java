package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoChangesException;
import com.sophie.store.backend.utils.exceptions.NoIdReceivedException;
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
class UpdateCategoryUseCaseTest {

    @InjectMocks
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    private static ErrorMessages errorMessages;
    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        categoryData = new CategoryData();
    }

    @Test
    void updateSuccess() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(categoryRepository.update(any(Category.class))).thenReturn(categoryData.getCategoryUpdated());

        Category response = updateCategoryUseCase.update(categoryData.getCategoryToUpdate());

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryUpdated());

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository).update(any(Category.class));
    }

    @Test
    void updateFailedNoIdReceivedException() {
        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateCategoryUseCase.update(categoryData.getCategoryToUpdateNoId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(categoryRepository, never()).findById(any(Long.class));
        verify(categoryRepository, never()).update(any(Category.class));
    }

    @Test
    void updateFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateCategoryUseCase.update(categoryData.getCategoryToUpdateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(categoryRepository, never()).findById(any(Long.class));
        verify(categoryRepository, never()).update(any(Category.class));
    }

    @Test
    void updateFailedNoResultsException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateCategoryUseCase.update(categoryData.getCategoryToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository, never()).update(any(Category.class));
    }

    @Test
    void updateFailedNoChangesException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateCategoryUseCase.update(categoryData.getCategoryActive())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository, never()).update(any(Category.class));
    }

}