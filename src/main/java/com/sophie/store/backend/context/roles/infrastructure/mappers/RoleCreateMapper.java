package com.sophie.store.backend.context.roles.infrastructure.mappers;

import com.sophie.store.backend.context.roles.application.dto.RoleCreateDTO;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleCreateMapper implements Mapper<RoleEntity, Role, RoleCreateDTO> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .name(entity.getName())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .name(model.getName())
                .build();
    }

    @Override
    public RoleCreateDTO modelToDto(Role model) {
        return RoleCreateDTO.builder()
                .name(model.getName())
                .build();
    }

    @Override
    public Role dtoToModel(RoleCreateDTO dto) {
        return Role.builder()
                .name(dto.getName())
                .build();
    }

    @Override
    public List<Role> entitiesToModels(List<RoleEntity> entities) {
        return entities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> modelsToEntities(List<Role> models) {
        return models.stream()
                .map(this::modelToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleCreateDTO> modelsToDtos(List<Role> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> dtosToModels(List<RoleCreateDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
