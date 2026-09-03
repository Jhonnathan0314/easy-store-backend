package com.easy.store.backend.context.store_request.infrastructure.mappers;

import com.easy.store.backend.context.store_request.application.dto.StoreRequestCreateDTO;
import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.infrastructure.persistence.StoreRequestEntity;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import com.easy.store.backend.utils.mappers.BaseMapper;

public class StoreRequestCreateMapper extends BaseMapper<StoreRequestEntity, StoreRequest, StoreRequestCreateDTO> {

    @Override
    public StoreRequest entityToModel(StoreRequestEntity entity) {
        return StoreRequest.builder()
                .id(entity.getId())
                .user(User.builder().id(entity.getUser().getId()).build())
                .storeName(entity.getStoreName())
                .storeDescription(entity.getStoreDescription())
                .status(entity.getStatus())
                .reviewedBy(entity.getReviewedBy())
                .build();
    }

    @Override
    public StoreRequestEntity modelToEntity(StoreRequest model) {
        return StoreRequestEntity.builder()
                .user(UserEntity.builder().id(model.getUser().getId()).build())
                .storeName(model.getStoreName())
                .storeDescription(model.getStoreDescription())
                .status(model.getStatus())
                .build();
    }

    @Override
    public StoreRequestCreateDTO modelToDto(StoreRequest model) {
        return StoreRequestCreateDTO.builder()
                .storeName(model.getStoreName())
                .storeDescription(model.getStoreDescription())
                .build();
    }

    @Override
    public StoreRequest dtoToModel(StoreRequestCreateDTO dto) {
        return StoreRequest.builder()
                .storeName(dto.getStoreName())
                .storeDescription(dto.getStoreDescription())
                .build();
    }

}
