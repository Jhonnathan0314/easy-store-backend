package com.easy.store.backend.context.roles.infrastructure.mappers;

import com.easy.store.backend.context.roles.application.dto.RoleResponseDTO;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class RoleResponseMapper extends BaseMapper<RoleEntity, Role, RoleResponseDTO> {

    @Override
    public Role entityToModel(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public RoleEntity modelToEntity(Role model) {
        return RoleEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    @Override
    public RoleResponseDTO modelToDto(Role model) {
        return RoleResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }

    @Override
    public Role dtoToModel(RoleResponseDTO dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

}
