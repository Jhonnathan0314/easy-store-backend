package com.easy.store.backend.context.purchase.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface PurchaseJpaRepository extends JpaRepository<PurchaseEntity, Long> {

    @Query(nativeQuery = true, value = "select p.id, p.user_id, p.payment_type_id, p.category_id, p.total, p.state, p.create_by, p.update_by, p.creation_date, p.update_date from purchase p\n" +
            "inner join category c on c.id = p.category_id\n" +
            "inner join account a on a.id = c.account_id\n" +
            "where a.id = ?1")
    List<PurchaseEntity> findByAccountId(Long accountId);
    @Query(value = "select * from purchase where DATE(date) = ?", nativeQuery = true)
    List<PurchaseEntity> findByUserId(Long userId);
    List<PurchaseEntity> findByPaymentTypeId(Long paymentTypeId);
    List<PurchaseEntity> findByCreationDate(Timestamp creationDate);
    List<PurchaseEntity> findByCreationDateBetween(Timestamp fromDate, Timestamp toDate);
    List<PurchaseEntity> findByTotalBetween(BigDecimal fromTotal, BigDecimal toTotal);

}
