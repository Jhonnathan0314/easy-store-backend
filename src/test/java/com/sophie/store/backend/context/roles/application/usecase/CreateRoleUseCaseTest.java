package com.sophie.store.backend.context.roles.application.usecase;

import com.sophie.store.backend.context.roles.data.RoleData;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.domain.port.RoleRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
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
class CreateRoleUseCaseTest {

    @InjectMocks
    private CreateRoleUseCase createRoleUseCase;

    @Mock
    private RoleRepository roleRepository;

    private static RoleData roleData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void createSuccess() throws InvalidBodyException, DuplicatedException {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(roleRepository.create(any(Role.class))).thenReturn(roleData.getRoleCreateValid());

        Role response = createRoleUseCase.create(roleData.getRoleCreateValid());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleCreateValid());

        verify(roleRepository).findByName(any(String.class));
        verify(roleRepository).create(any(Role.class));
    }

    @Test
    void createFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> createRoleUseCase.create(roleData.getRoleCreateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(roleRepository, never()).findByName(any(String.class));
        verify(roleRepository, never()).create(any(Role.class));
    }

    @Test
    void createFailedDuplicatedException() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        DuplicatedException exception = assertThrows(
                DuplicatedException.class,
                () -> createRoleUseCase.create(roleData.getRoleActive())
        );

        assertEquals(exception.getMessage(), errorMessages.DUPLICATED);

        verify(roleRepository).findByName(any(String.class));
        verify(roleRepository, never()).create(any(Role.class));
    }
}