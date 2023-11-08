package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
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
class ChangeStateByIdCategoryUseCaseTest {

    @InjectMocks
    private ChangeStateByIdCategoryUseCase changeStateByIdCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    private static CategoryData categoryData;
    private static ErrorMessages errorMessages;
    private static Long categoryId;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
        errorMessages = new ErrorMessages();
        categoryId = 1L;
    }

    @Test
    void successInactivePerson() throws NonExistenceException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));
        when(categoryRepository.update(any(Category.class))).thenReturn(categoryData.getCategoryInactive());

        Category response = changeStateByIdCategoryUseCase.changeStateById(categoryData.getCategoryActive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), categoryData.getCategoryInactive().getState());

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository).update(any(Category.class));
    }

    @Test
    void successActivePerson() throws NonExistenceException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryInactive()));
        when(categoryRepository.update(any(Category.class))).thenReturn(categoryData.getCategoryActive());

        Category response = changeStateByIdCategoryUseCase.changeStateById(categoryData.getCategoryInactive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), categoryData.getCategoryActive().getState());

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository).update(any(Category.class));
    }

    @Test
    void failedChangeStateByIdNonExistenceException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> changeStateByIdCategoryUseCase.changeStateById(categoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).update(any(Category.class));
    }
}