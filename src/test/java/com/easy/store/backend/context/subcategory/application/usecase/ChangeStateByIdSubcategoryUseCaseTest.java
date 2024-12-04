package com.easy.store.backend.context.subcategory.application.usecase;

import com.easy.store.backend.context.subcategory.data.SubcategoryData;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.domain.port.SubcategoryRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
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
class ChangeStateByIdSubcategoryUseCaseTest {

    @InjectMocks
    private ChangeStateByIdSubcategoryUseCase changeStateByIdSubcategoryUseCase;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    private static SubcategoryData subcategoryData;
    private static ErrorMessages errorMessages;
    private static Long subcategoryId;

    @BeforeAll
    static void setUp() {
        subcategoryData = new SubcategoryData();
        errorMessages = new ErrorMessages();
        subcategoryId = 1L;
    }

    @Test
    void successInactivePerson() throws NonExistenceException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));
        when(subcategoryRepository.update(any(Subcategory.class))).thenReturn(subcategoryData.getSubcategoryInactive());

        Subcategory response = changeStateByIdSubcategoryUseCase.changeStateById(subcategoryData.getSubcategoryActive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), subcategoryData.getSubcategoryInactive().getState());

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository).update(any(Subcategory.class));
    }

    @Test
    void successActivePerson() throws NonExistenceException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryInactive()));
        when(subcategoryRepository.update(any(Subcategory.class))).thenReturn(subcategoryData.getSubcategoryActive());

        Subcategory response = changeStateByIdSubcategoryUseCase.changeStateById(subcategoryData.getSubcategoryInactive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), subcategoryData.getSubcategoryActive().getState());

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository).update(any(Subcategory.class));
    }

    @Test
    void failedChangeStateByIdNonExistenceException() {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> changeStateByIdSubcategoryUseCase.changeStateById(subcategoryId)
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(subcategoryRepository).findById(subcategoryId);
        verify(subcategoryRepository, never()).update(any(Subcategory.class));
    }
}