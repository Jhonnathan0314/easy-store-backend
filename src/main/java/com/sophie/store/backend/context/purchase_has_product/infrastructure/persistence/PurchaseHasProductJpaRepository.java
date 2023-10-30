package com.sophie.store.backend.context.purchase_has_product.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHasProductJpaRepository extends JpaRepository<PurchaseHasProductEntity, Long> {

    List<PurchaseHasProductEntity> findAllByPurchaseId(Long purchaseId);
    List<PurchaseHasProductEntity> findAllByProductId(Long productId);

}
