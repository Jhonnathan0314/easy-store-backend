package com.sophie.store.backend.context.roles.infrastructure.adapter;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.mappers.RoleMapper;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleJpaRepository;
import com.sophie.store.backend.context.roles.data.RoleData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryJpaAdapterTest {

    @InjectMocks
    private RoleRepositoryJpaAdapter roleRepositoryJpaAdapter;


    @Mock
    private RoleJpaRepository roleJpaRepository;

    private static RoleMapper roleMapper;
    private static RoleData roleData;

    @BeforeAll
    static void setUp() {
        roleData = new RoleData();
        roleMapper = new RoleMapper();
    }

    @Test
    @Order(0)
    void findAllTest() {
        List<RoleEntity> mockEntities = roleMapper.modelsToEntities(roleData.getRolesList());
        when(roleJpaRepository.findAll()).thenReturn(mockEntities);

        List<Role> response = roleRepositoryJpaAdapter.findAll();

        assertNotNull(response);
        assertEquals(mockEntities.size(), response.size());

        verify(roleJpaRepository).findAll();
    }

    @Test
    @Order(1)
    void findByIdTest() {
        RoleEntity mockEntity = roleMapper.modelToEntity(roleData.getRoleResponseOne());
        when(roleJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(mockEntity));

        Role response = roleRepositoryJpaAdapter.findById(1L).orElse(null);

        assertNotNull(response);
        assertEquals(response, roleData.getRoleResponseOne());

        verify(roleJpaRepository).findById(any(Long.class));
    }

    @Test
    @Order(2)
    void findByNameTest() {
        RoleEntity mockEntity = roleMapper.modelToEntity(roleData.getRoleResponseOne());
        when(roleJpaRepository.findByName(any(String.class))).thenReturn(Optional.of(mockEntity));

        Role response = roleRepositoryJpaAdapter.findByName("test").orElse(null);

        assertNotNull(response);
        assertEquals(response, roleData.getRoleResponseOne());

        verify(roleJpaRepository).findByName(any(String.class));
    }

    @Test
    @Order(3)
    void createTest() {
        RoleEntity mockEntity = roleMapper.modelToEntity(roleData.getRoleResponseOne());
        when(roleJpaRepository.save(any(RoleEntity.class))).thenReturn(mockEntity);

        Role response = roleRepositoryJpaAdapter.create(roleData.getRoleCreateValid());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleResponseOne());

        verify(roleJpaRepository).save(any(RoleEntity.class));
    }

    @Test
    @Order(4)
    void updateTest() {
        RoleEntity mockEntity = roleMapper.modelToEntity(roleData.getRoleResponseOne());
        when(roleJpaRepository.save(any(RoleEntity.class))).thenReturn(mockEntity);

        Role response = roleRepositoryJpaAdapter.update(roleData.getRoleActive());

        assertNotNull(response);
        assertEquals(response, roleData.getRoleResponseOne());

        verify(roleJpaRepository).save(any(RoleEntity.class));
    }

    @Test
    @Order(5)
    void deleteByIdTest() {
        roleRepositoryJpaAdapter.deleteById(1L);

        verify(roleJpaRepository).deleteById(any(Long.class));
    }
}