package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdCategoryUseCaseTest {

    @InjectMocks
    private FindByIdCategoryUseCase findByIdCategoryUseCase;

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
    void findByIdSuccess() throws NoResultsException {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        Category response = findByIdCategoryUseCase.findById(categoryData.getCategoryActive().getId());

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryActive());

        verify(categoryRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdCategoryUseCase.findById(categoryData.getCategoryActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(categoryRepository).findById(any(Long.class));
    }

}