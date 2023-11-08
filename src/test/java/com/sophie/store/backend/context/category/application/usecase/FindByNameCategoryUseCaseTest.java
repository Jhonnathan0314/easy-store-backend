package com.sophie.store.backend.context.category.application.usecase;

import com.sophie.store.backend.context.category.data.CategoryData;
import com.sophie.store.backend.context.category.domain.model.Category;
import com.sophie.store.backend.context.category.domain.port.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByNameCategoryUseCaseTest {

    @InjectMocks
    private FindByNameCategoryUseCase findByNameCategoryUseCase;

    @Mock
    private CategoryRepository categoryRepository;

    private static CategoryData categoryData;

    @BeforeAll
    static void setUp() {
        categoryData = new CategoryData();
    }

    @Test
    void findByNameSuccess() {
        when(categoryRepository.findByName(any(String.class))).thenReturn(Optional.of(categoryData.getCategoryActive()));

        Category response = findByNameCategoryUseCase.findByName(categoryData.getCategoryActive().getName()).orElse(null);

        assertNotNull(response);
        assertEquals(response, categoryData.getCategoryActive());

        verify(categoryRepository).findByName(any(String.class));
    }

}