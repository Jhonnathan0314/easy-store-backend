package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.data.UserData;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
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
class FindByUsernameUserUseCaseTest {

    @InjectMocks
    private FindByUsernameUserUseCase findByUsernameUserUseCase;

    @Mock
    private UserRepository userRepository;

    private static UserData userData;

    @BeforeAll
    static void setUp() {
        userData = new UserData();
    }

    @Test
    void findByUsernameSuccess() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(userData.getUserActive()));

        User response = findByUsernameUserUseCase.findByUsername(userData.getUserActive().getUsername()).orElse(null);

        assertNotNull(response);
        assertEquals(response, userData.getUserActive());

        verify(userRepository).findByUsername(any(String.class));
    }

}