package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
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
class CreateCategoryUseCaseTest {

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    private static CategoryData categoryData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void createSuccess() throws InvalidBodyException, DuplicatedException {
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(categoryRepository.create(any(Category.class))).thenReturn(categoryData.getCategoryCreateValid());

        Category response = createCategoryUseCase.create(categoryData.getCategoryCreateValid());

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryCreateValid());

        verify(categoryRepository).findByName(any(String.class));
        verify(categoryRepository).create(any(Category.class));
    }

    @Test
    void createFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createCategoryUseCase.create(categoryData.getCategoryCreateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(categoryRepository, never()).findByName(any(String.class));
        verify(categoryRepository, never()).create(any(Category.class));
    }

    @Test
    void createFailedDuplicatedException() {
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createCategoryUseCase.create(categoryData.getCategoryActive())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(categoryRepository).findByName(any(String.class));
        verify(categoryRepository, never()).create(any(Category.class));
    }
}