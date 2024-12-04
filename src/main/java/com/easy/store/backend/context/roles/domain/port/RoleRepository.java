package com.easy.store.backend.context.roles.domain.port;

import com.easy.store.backend.context.roles.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    Role create(Role role);
    Role update(Role role);
    void deleteById(Long id);
}
