package com.sophie.store.backend.context.subcategory.application.usecase;

import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
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
class DeleteByIdSubcategoryUseCaseTest {

    @InjectMocks
    private DeleteByIdSubcategoryUseCase deleteByIdSubcategoryUseCase;

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
    void deleteByIdSuccess() throws NonExistenceException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(subcategoryData.getSubcategoryActive()));

        deleteByIdSubcategoryUseCase.deleteById(subcategoryData.getSubcategoryActive().getId());

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdFailedNonExistenceException() throws NonExistenceException {
        when(subcategoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdSubcategoryUseCase.deleteById(subcategoryData.getSubcategoryActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(subcategoryRepository).findById(any(Long.class));
        verify(subcategoryRepository, never()).deleteById(any(Long.class));
    }

}