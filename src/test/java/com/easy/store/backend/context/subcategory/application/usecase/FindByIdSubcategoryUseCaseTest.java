package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdSubcategoryUseCaseTest {

    @InjectMocks
    private FindByIdSubcategoryUseCase findByIdSubcategoryUseCase;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    private static ErrorMessages errorMessages;
    private static SubcategoryData subcategoryData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        subcategoryData = new SubcategoryData();
    }

    @Test
    void findByIdSuccess() throws NoResultsException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        Subcategory response = findByIdSubcategoryUseCase.findById(subcategoryData.getSubcategoryActive().getId());

        assertNotNull(response);
        assertEquals(response, subcategoryData.getSubcategoryActive());

        verify(subcategoryRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdSubcategoryUseCase.findById(subcategoryData.getSubcategoryActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(subcategoryRepository).findById(any(Long.class));
    }

}