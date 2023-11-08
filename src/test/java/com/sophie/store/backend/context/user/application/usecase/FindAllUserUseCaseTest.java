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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUserUseCaseTest {

    @InjectMocks
    private FindAllUserUseCase findAllUserUseCase;

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
    void findAllUserSuccess() throws NoResultsException {
        when(userRepository.findAll()).thenReturn(userData.getUsersList());

        List<User> response = findAllUserUseCase.findAll();

        assertNotNull(response);
        assertNotEquals(response.size(), 0);
    }

    @Test
    void findAllUserFailedNoResultsException() {
        when(userRepository.findAll()).thenReturn(new LinkedList<>());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () ->  findAllUserUseCase.findAll()
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(userRepository).findAll();
    }

}