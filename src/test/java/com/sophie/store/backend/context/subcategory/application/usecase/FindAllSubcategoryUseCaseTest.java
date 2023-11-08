package com.sophie.store.backend.context.subcategory.application.usecase;

import com.sophie.store.backend.context.subcategory.data.SubcategoryData;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.domain.port.SubcategoryRepository;
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
class FindAllSubcategoryUseCaseTest {

    @InjectMocks
    private FindAllSubcategoryUseCase findAllSubcategoryUseCase;

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
    void findAllSubcategorySuccess() throws NoResultsException {
        when(subcategoryRepository.findAll()).thenReturn(subcategoryData.getSubcategorysList());

        List<Subcategory> response = findAllSubcategoryUseCase.findAll();

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllSubcategoryFailedNoResultsException() {
        when(subcategoryRepository.findAll()).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllSubcategoryUseCase.findAll()
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(subcategoryRepository).findAll();
    }

}