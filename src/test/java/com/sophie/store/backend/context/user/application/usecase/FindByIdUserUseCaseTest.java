package com.sophie.store.backend.context.user.application.usecase;

import com.sophie.store.backend.context.user.data.UserData;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.domain.port.UserRepository;
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
class FindByIdUserUseCaseTest {

    @InjectMocks
    private FindByIdUserUseCase findByIdUserUseCase;

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
    void findByIdSuccess() throws NoResultsException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));

        User response = findByIdUserUseCase.findById(userData.getUserActive().getId());

        assertNotNull(response);
        assertEquals(response, userData.getUserActive());

        verify(userRepository).findById(any(Long.class));
    }

    @Test
    void findByIdFailedNoResultsException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> findByIdUserUseCase.findById(userData.getUserActive().getId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(userRepository).findById(any(Long.class));
    }

}