package com.easy.store.backend.context.purchase_has_product.infrastructure.persistence;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseHasProductJpaRepository extends JpaRepository<PurchaseHasProductEntity, PurchaseHasProductId> {

    Optional<PurchaseHasProductEntity> findByPurchaseIdAndProductId(Long purchase_id, Long product_id);
    List<PurchaseHasProductEntity> findByPurchaseId(Long purchaseId);

}
