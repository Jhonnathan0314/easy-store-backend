package com.easy.store.backend.context.user.domain.port;

import com.easy.store.backend.context.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String name);
    User create(User user);
    User update(User user);
    void deleteById(Long id);
}
