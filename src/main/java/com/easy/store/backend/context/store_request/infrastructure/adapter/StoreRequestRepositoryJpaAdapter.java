package com.easy.store.backend.context.store_request.infrastructure.adapter;

import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.context.store_request.infrastructure.mappers.StoreRequestCreateMapper;
import com.easy.store.backend.context.store_request.infrastructure.mappers.StoreRequestResponseMapper;
import com.easy.store.backend.context.store_request.infrastructure.persistence.StoreRequestEntity;
import com.easy.store.backend.context.store_request.infrastructure.persistence.StoreRequestJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreRequestRepositoryJpaAdapter implements StoreRequestRepository {

    private final StoreRequestJpaRepository storeRequestJpaRepository;
    private final StoreRequestCreateMapper createMapper = new StoreRequestCreateMapper();
    private final StoreRequestResponseMapper responseMapper = new StoreRequestResponseMapper();

    @Override
    public Optional<StoreRequest> findById(Long id) {
        Optional<StoreRequestEntity> optEntity = storeRequestJpaRepository.findById(id);
        return optEntity.map(responseMapper::entityToModel);
    }

    @Override
    public List<StoreRequest> findByUserId(Long userId) {
        List<StoreRequestEntity> entities = storeRequestJpaRepository.findByUserId(userId);
        return responseMapper.entitiesToModels(entities);
    }

    @Override
    public List<StoreRequest> findPendingRequests() {
        List<StoreRequestEntity> entities = storeRequestJpaRepository.findByStatus(StoreRequest.STATUS_PENDING);
        return responseMapper.entitiesToModels(entities);
    }

    @Override
    public Optional<StoreRequest> findLatestPendingByUserId(Long userId) {
        Optional<StoreRequestEntity> optEntity = storeRequestJpaRepository
                .findFirstByUserIdAndStatusOrderByIdDesc(userId, StoreRequest.STATUS_PENDING);
        return optEntity.map(responseMapper::entityToModel);
    }

    @Override
    public StoreRequest create(StoreRequest storeRequest) {
        StoreRequestEntity saved = storeRequestJpaRepository.save(createMapper.modelToEntity(storeRequest));
        // The incoming model already carries the fully resolved User (the use case looked it up
        // before calling create), so reuse it instead of the persisted entity's stub reference
        // (JPA's first-level cache would otherwise return the same id-only UserEntity we saved).
        return StoreRequest.builder()
                .id(saved.getId())
                .user(storeRequest.getUser())
                .storeName(saved.getStoreName())
                .storeDescription(saved.getStoreDescription())
                .status(saved.getStatus())
                .reviewedBy(saved.getReviewedBy())
                .creationDate(saved.getCreationDate())
                .updateDate(saved.getUpdateDate())
                .build();
    }

    @Override
    public StoreRequest update(StoreRequest storeRequest) {
        StoreRequestEntity entity = storeRequestJpaRepository.save(responseMapper.modelToEntity(storeRequest));
        return responseMapper.entityToModel(entity);
    }
}
