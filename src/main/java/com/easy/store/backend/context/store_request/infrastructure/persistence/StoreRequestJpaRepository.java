package com.easy.store.backend.context.store_request.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRequestJpaRepository extends JpaRepository<StoreRequestEntity, Long> {

    List<StoreRequestEntity> findByUserId(Long userId);
    List<StoreRequestEntity> findByStatus(String status);
    Optional<StoreRequestEntity> findFirstByUserIdAndStatusOrderByIdDesc(Long userId, String status);

}
