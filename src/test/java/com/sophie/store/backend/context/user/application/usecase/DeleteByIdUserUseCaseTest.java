package com.sophie.store.backend.context.user.application.usecase;

import com.sophie.store.backend.context.user.data.UserData;
import com.sophie.store.backend.context.user.domain.port.UserRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
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
class DeleteByIdUserUseCaseTest {

    @InjectMocks
    private DeleteByIdUserUseCase deleteByIdUserUseCase;

    @Mock
    private UserRepository userRepository;

    private static ErrorMessages errorMessages;
    private static UserData userData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        userData = new UserData();
    }

    @Test
    void deleteByIdSuccess() throws NonExistenceException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));

        deleteByIdUserUseCase.deleteById(userData.getUserActive().getId());

        verify(userRepository).findById(any(Long.class));
        verify(userRepository).deleteById(any(Long.class));
    }

    @Test
    void deleteByIdFailedNonExistenceException() throws NonExistenceException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> deleteByIdUserUseCase.deleteById(userData.getUserActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(userRepository).findById(any(Long.class));
        verify(userRepository, never()).deleteById(any(Long.class));
    }

}