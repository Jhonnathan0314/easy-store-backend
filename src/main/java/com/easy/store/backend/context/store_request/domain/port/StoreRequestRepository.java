package com.easy.store.backend.context.store_request.domain.port;

import com.easy.store.backend.context.store_request.domain.model.StoreRequest;

import java.util.List;
import java.util.Optional;

public interface StoreRequestRepository {
    Optional<StoreRequest> findById(Long id);
    List<StoreRequest> findByUserId(Long userId);
    List<StoreRequest> findPendingRequests();
    Optional<StoreRequest> findLatestPendingByUserId(Long userId);
    StoreRequest create(StoreRequest storeRequest);
    StoreRequest update(StoreRequest storeRequest);
}
