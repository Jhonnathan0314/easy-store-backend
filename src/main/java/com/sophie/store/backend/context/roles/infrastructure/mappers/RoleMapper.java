package com.sophie.store.backend.context.roles.infrastructure.mappers;

import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.sophie.store.backend.utils.mappers.Mapper;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper implements Mapper<RoleEntity, Role, RoleDTO> {
    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .creationDate(entity.getCreationDate())
                .state(entity.getState())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .creationDate(model.getCreationDate())
                .state(model.getState())
                .build();
    }

    @Override
    public RoleDTO modelToDto(Role model) {
        return RoleDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .creationDate(model.getCreationDate())
                .state(model.getState())
                .build();
    }

    @Override
    public Role dtoToModel(RoleDTO dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .creationDate(dto.getCreationDate())
                .state(dto.getState())
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
    public List<RoleDTO> modelsToDtos(List<Role> models) {
        return models.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> dtosToModels(List<RoleDTO> dtos) {
        return dtos.stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}
