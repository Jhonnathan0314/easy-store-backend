package com.easy.store.backend.context.codes.infrastructure.mapper;

import com.easy.store.backend.context.codes.application.dto.CodeDTO;
import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.infrastructure.persistence.CodeEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class CodeMapper extends BaseMapper<CodeEntity, Code, CodeDTO> {

    @Override
    public Code entityToModel(CodeEntity entity) {
        return Code.builder()
                .userId(entity.getUserId())
                .code(entity.getCode())
                .action(entity.getAction())
                .creationDate(entity.getCreationDate())
                .build();
    }

    @Override
    public CodeEntity modelToEntity(Code model) {
        return CodeEntity.builder()
                .userId(model.getUserId())
                .code(model.getCode())
                .action(model.getAction())
                .creationDate(model.getCreationDate())
                .build();
    }

    @Override
    public CodeDTO modelToDto(Code model) {
        return CodeDTO.builder()
                .userId(model.getUserId())
                .code(model.getCode())
                .action(model.getAction())
                .creationDate(model.getCreationDate())
                .build();
    }

    @Override
    public Code dtoToModel(CodeDTO dto) {
        return Code.builder()
                .userId(dto.getUserId())
                .code(dto.getCode())
                .action(dto.getAction())
                .creationDate(dto.getCreationDate())
                .build();
    }

}
