package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.context.roles.data.RoleData;
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
class FindByNameRoleUseCaseTest {

    @InjectMocks
    private FindByNameRoleUseCase findByNameRoleUseCase;

    @Mock
    private RoleRepository roleRepository;

    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
    }

    @Test
    void findByNameSuccess() {
        when(roleRepository.findByName(any(String.class))).thenReturn(Optional.of(roleData.getRoleActive()));

        Role response = findByNameRoleUseCase.findByName(roleData.getRoleActive().getName()).orElse(null);

        assertNotNull(response);
        assertEquals(response, roleData.getRoleActive());

        verify(roleRepository).findByName(any(String.class));
    }

}