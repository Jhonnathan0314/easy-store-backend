package com.sophie.store.backend.context.roles.application.usecase;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.domain.port.RoleRepository;
import com.sophie.store.backend.context.roles.data.RoleData;
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
class ChangeStateByIdRoleUseCaseTest {

    @InjectMocks
    private ChangeStateByIdRoleUseCase changeStateByIdRoleUseCase;

    @Mock
    private RoleRepository roleRepository;

    private static RoleData roleData;
    private static ErrorMessages errorMessages;
    private static Long roleId;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
        errorMessages = new ErrorMessages();
        roleId = 1L;
    }

    @Test
    void successInactivePerson() throws NonExistenceException {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));
        when(roleRepository.update(any(Role.class))).thenReturn(roleData.getRoleInactive());

        Role response = changeStateByIdRoleUseCase.changeStateById(roleData.getRoleActive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), roleData.getRoleInactive().getState());

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository).update(any(Role.class));
    }

    @Test
    void successActivePerson() throws NonExistenceException {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleInactive()));
        when(roleRepository.update(any(Role.class))).thenReturn(roleData.getRoleActive());

        Role response = changeStateByIdRoleUseCase.changeStateById(roleData.getRoleInactive().getId());

        assertNotNull(response);
        assertEquals(response.getState(), roleData.getRoleActive().getState());

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository).update(any(Role.class));
    }

    @Test
    void failedChangeStateByIdNonExistenceException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NonExistenceException exception = assertThrows(
                NonExistenceException.class,
                () -> changeStateByIdRoleUseCase.changeStateById(roleId)
        );

        assertEquals(exception.getMessage(), errorMessages.NON_EXISTENT_DATA);

        verify(roleRepository).findById(roleId);
        verify(roleRepository, never()).update(any(Role.class));
    }
}