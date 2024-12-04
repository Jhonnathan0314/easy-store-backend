package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.data.RoleData;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRoleUseCaseTest {

    @InjectMocks
    private UpdateRoleUseCase updateRoleUseCase;

    @Mock
    private RoleRepository roleRepository;

    private static ErrorMessages errorMessages;
    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        errorMessages = new ErrorMessages();
        roleData = new RoleData();
    }

    @Test
    void updateSuccess() throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));
        when(roleRepository.update(any(Role.class))).thenReturn(roleData.getRoleUpdated());

        Role response = updateRoleUseCase.update(roleData.getRoleToUpdate());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleUpdated());

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository).update(any(Role.class));
    }

    @Test
    void updateFailedNoIdReceivedException() {
        NoIdReceivedException exception = assertThrows(
                NoIdReceivedException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdateNoId())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_ID_RECEIVED);

        verify(roleRepository, never()).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

    @Test
    void updateFailedInvalidBodyException() {
        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdateInvalid())
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(roleRepository, never()).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

    @Test
    void updateFailedNoResultsException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> updateRoleUseCase.update(roleData.getRoleToUpdate())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_RESULTS);

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

    @Test
    void updateFailedNoChangesException() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        NoChangesException exception = assertThrows(
                NoChangesException.class,
                () -> updateRoleUseCase.update(roleData.getRoleActive())
        );

        assertEquals(exception.getMessage(), errorMessages.NO_CHANGES);

        verify(roleRepository).findById(any(Long.class));
        verify(roleRepository, never()).update(any(Role.class));
    }

}