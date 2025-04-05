package com.easy.store.backend.context.user.infrastructure.mappers;

import com.easy.store.backend.context.user.application.dto.UserUpdateDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class UserUpdateMapper extends BaseMapper<UserEntity, User, UserUpdateDTO> {

    @Override
    public User entityToModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .state(entity.getState())
                .build();
    }

    @Override
    public UserEntity modelToEntity(User model) {
        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .state(model.getState())
                .build();
    }

    @Override
    public UserUpdateDTO modelToDto(User model) {
        return UserUpdateDTO.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastName(model.getLastName())
                .password(model.getPassword())
                .build();
    }

    @Override
    public User dtoToModel(UserUpdateDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
    }

}
