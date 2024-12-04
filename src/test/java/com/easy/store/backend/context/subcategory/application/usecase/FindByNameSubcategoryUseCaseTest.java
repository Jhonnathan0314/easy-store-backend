package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
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
class FindByNameSubcategoryUseCaseTest {

    @InjectMocks
    private FindByNameSubcategoryUseCase findByNameSubcategoryUseCase;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
    }

    @Test
    void findByNameSuccess() {
        when(subcategoryRepository.findByName(any(String.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        Subcategory response = findByNameSubcategoryUseCase.findByName(subcategoryData.getSubcategoryActive().getName()).orElse(null);

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryActive());

        verify(subcategoryRepository).findByName(any(String.class));
    }

}