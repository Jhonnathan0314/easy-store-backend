package com.easy.store.backend.context.purchase.domain.port;

import com.easy.store.backend.context.purchase.domain.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> findAll();
    Optional<Purchase> findById(Long id);
    List<Purchase> findByAccountId(Long accountId);
    List<Purchase> findByCategoryId(Long categoryId);
    List<Purchase> findByUserId(Long userId);
    Purchase generate(Purchase purchase);
    Purchase update(Purchase purchase);
    void deleteById(Long id);
}
