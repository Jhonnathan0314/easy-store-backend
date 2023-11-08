package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteByIdCategoryUseCaseTest {

    @InjectMocks
    private DeleteByIdCategoryUseCase deleteByIdCategoryUseCase;

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
    void deleteByIdSuccess() throws NonExistenceException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        deleteByIdCategoryUseCase.deleteById(categoryData.getCategoryActive().getId());

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdFailedNonExistenceException() throws NonExistenceException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdCategoryUseCase.deleteById(categoryData.getCategoryActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(categoryRepository).findById(any(Long.class));
        verify(categoryRepository, never()).deleteById(any(Long.class));
    }

}