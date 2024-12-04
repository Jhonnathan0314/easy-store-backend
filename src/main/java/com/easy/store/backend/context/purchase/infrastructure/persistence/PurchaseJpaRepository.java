package com.easy.store.backend.context.purchase.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface PurchaseJpaRepository extends JpaRepository<PurchaseEntity, Long> {

    @Query(value = "select * from purchase where DATE(date) = ?", nativeQuery = true)
    List<PurchaseEntity> findAllByDate(Timestamp date);
    List<PurchaseEntity> findAllByUserId(Long userId);
    List<PurchaseEntity> findAllByPaymentTypeId(Long paymentTypeId);
    List<PurchaseEntity> findByDateBetween(Timestamp fromDate, Timestamp toDate);
    List<PurchaseEntity> findByTotalBetween(BigDecimal fromTotal, BigDecimal toTotal);

}
