package com.easy.store.backend.context.purchase_has_product.domain.port;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;

import java.util.List;
import java.util.Optional;

public interface PurchaseHasProductRepository {

    Optional<PurchaseHasProduct> findByPurchaseIdAndProductId(PurchaseHasProductId id);
    List<PurchaseHasProduct> findByPurchaseId(Long purchaseId);
    PurchaseHasProduct add(PurchaseHasProduct purchaseHasProduct);
    List<PurchaseHasProduct> addAll(List<PurchaseHasProduct> purchaseHasProducts);
    PurchaseHasProduct update(PurchaseHasProduct purchaseHasProduct);
    void removeByPurchaseIdAndProductId(PurchaseHasProductId id);
    void removeAll(List<PurchaseHasProductId> ids);

}
