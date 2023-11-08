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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllCategoryUseCaseTest {

    @InjectMocks
    private FindAllCategoryUseCase findAllCategoryUseCase;

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
    void findAllCategorySuccess() throws NoResultsException {
        when(categoryRepository.findAll()).thenReturn(categoryData.getCategorysList());

        List<Category> response = findAllCategoryUseCase.findAll();

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllCategoryFailedNoResultsException() {
        when(categoryRepository.findAll()).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllCategoryUseCase.findAll()
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(categoryRepository).findAll();
    }

}