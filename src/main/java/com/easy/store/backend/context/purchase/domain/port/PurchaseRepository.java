package com.easy.store.backend.context.purchase.domain.port;

import com.easy.store.backend.context.purchase.domain.model.Purchase;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> findAll();
    Optional<Purchase> findById(Long id);
    List<Purchase> findByUserId(Long userId);
    List<Purchase> findByPaymentTypeId(Long paymentTypeId);
    List<Purchase> findByCreationDate(Timestamp creationDate);
    List<Purchase> findByCreationDateBetween(Timestamp fromDate, Timestamp toDate);
    List<Purchase> findByTotalBetween(BigDecimal fromTotal, BigDecimal toTotal);
    Purchase generate(Purchase purchase);
    Purchase update(Purchase purchase);
    void deleteById(Long id);
}
