package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.data.UserData;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static ErrorMessages errorMessages;
    private static UserData userData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        userData = new UserData();
    }

    @Test
    void updateSuccess() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(userData.getEncodedPassword());
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));
        when(userRepository.update(any(User.class))).thenReturn(userData.getUserUpdated());

        User response = updateUserUseCase.update(userData.getUserToUpdate());

        assertNotNull(response);
        assertEquals(response, userData.getUserUpdated());

        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository).findById(any(Long.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void updateSuccessNoPassword() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));
        when(userRepository.update(any(User.class))).thenReturn(userData.getUserUpdated());

        User response = updateUserUseCase.update(userData.getUserToUpdateNoPassword());

        assertNotNull(response);
        assertEquals(response, userData.getUserUpdated());

        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository).findById(any(Long.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void updateFailedNoIdReceivedException() {
        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdateNoId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(userRepository, never()).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(userRepository, never()).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateFailedNoResultsException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateUserUseCase.update(userData.getUserToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(userRepository).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void updateFailedNoChangesException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateUserUseCase.update(userData.getUserActive())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(userRepository).findById(any(Long.class));
        verify(passwordEncoder, never()).encode(any(CharSequence.class));
        verify(userRepository, never()).update(any(User.class));
    }

}