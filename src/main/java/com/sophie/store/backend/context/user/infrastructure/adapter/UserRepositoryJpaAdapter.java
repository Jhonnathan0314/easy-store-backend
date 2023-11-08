package com.sophie.store.backend.context.user.infrastructure.adapter;

import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.domain.port.UserRepository;

import com.sophie.store.backend.context.user.infrastructure.mappers.UserCreateMapper;
import com.sophie.store.backend.context.user.infrastructure.mappers.UserMapper;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper mapper = new UserMapper();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userJpaRepository.findAll();
        return mapper.entitiesToModels(userEntities);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> optUserEntity = userJpaRepository.findById(id);
        return optUserEntity.map(mapper::entityToModel);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> optUserEntity = userJpaRepository.findByUsername(username);
        return optUserEntity.map(mapper::entityToModel);
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = userJpaRepository.save(userCreateMapper.modelToEntity(user));
        return mapper.entityToModel(userEntity);
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = userJpaRepository.save(mapper.modelToEntity(user));
        return mapper.entityToModel(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

}
