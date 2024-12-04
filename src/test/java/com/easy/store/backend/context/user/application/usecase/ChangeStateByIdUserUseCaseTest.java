package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.data.UserData;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
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
class ChangeStateByIdUserUseCaseTest {

    @InjectMocks
    private ChangeStateByIdUserUseCase changeStateByIdUserUseCase;

    @Mock
    private UserRepository userRepository;

    private static UserData userData;
    private static ErrorMessages errorMessages;
    private static Long userId;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
        errorMessages = new ErrorMessages();
        userId = 1L;
    }

    @Test
    void successInactivePerson() throws NonExistenceException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));
        when(userRepository.update(any(User.class))).thenReturn(userData.getUserInactive());

        User response = changeStateByIdUserUseCase.changeStateById(userData.getUserActive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), userData.getUserInactive().getState());

        verify(userRepository).findById(any(Long.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void successActivePerson() throws NonExistenceException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserInactive()));
        when(userRepository.update(any(User.class))).thenReturn(userData.getUserActive());

        User response = changeStateByIdUserUseCase.changeStateById(userData.getUserInactive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), userData.getUserActive().getState());

        verify(userRepository).findById(any(Long.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void failedChangeStateByIdNonExistenceException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> changeStateByIdUserUseCase.changeStateById(userId)
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).update(any(User.class));
    }
}