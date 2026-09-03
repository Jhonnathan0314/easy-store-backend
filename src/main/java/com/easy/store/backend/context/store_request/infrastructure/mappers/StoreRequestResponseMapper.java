package com.easy.store.backend.context.store_request.infrastructure.mappers;

import com.easy.store.backend.context.store_request.application.dto.StoreRequestResponseDTO;
import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.infrastructure.persistence.StoreRequestEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class StoreRequestResponseMapper extends BaseMapper<StoreRequestEntity, StoreRequest, StoreRequestResponseDTO> {

    @Override
    public StoreRequest entityToModel(StoreRequestEntity entity) {
        return StoreRequest.builder()
                .id(entity.getId())
                .user(User.builder()
                        .id(entity.getUser().getId())
                        .username(entity.getUser().getUsername())
                        .build()
                )
                .storeName(entity.getStoreName())
                .storeDescription(entity.getStoreDescription())
                .status(entity.getStatus())
                .reviewedBy(entity.getReviewedBy())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    @Override
    public StoreRequestEntity modelToEntity(StoreRequest model) {
        return StoreRequestEntity.builder()
                .id(model.getId())
                .user(UserEntity.builder().id(model.getUser().getId()).build())
                .storeName(model.getStoreName())
                .storeDescription(model.getStoreDescription())
                .status(model.getStatus())
                .reviewedBy(model.getReviewedBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .build();
    }

    @Override
    public StoreRequestResponseDTO modelToDto(StoreRequest model) {
        return StoreRequestResponseDTO.builder()
                .id(model.getId())
                .userId(model.getUser().getId())
                .username(model.getUser().getUsername())
                .storeName(model.getStoreName())
                .storeDescription(model.getStoreDescription())
                .status(model.getStatus())
                .reviewedBy(model.getReviewedBy())
                .creationDate(model.getCreationDate())
                .updateDate(model.getUpdateDate())
                .build();
    }

    @Override
    public StoreRequest dtoToModel(StoreRequestResponseDTO dto) {
        return StoreRequest.builder()
                .id(dto.getId())
                .user(User.builder().id(dto.getUserId()).username(dto.getUsername()).build())
                .storeName(dto.getStoreName())
                .storeDescription(dto.getStoreDescription())
                .status(dto.getStatus())
                .reviewedBy(dto.getReviewedBy())
                .creationDate(dto.getCreationDate())
                .updateDate(dto.getUpdateDate())
                .build();
    }

}
