package com.sophie.store.backend.context.purchase.infrastructure.adapter;

import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.context.purchase.infrastructure.mappers.PurchaseMapper;
import com.sophie.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.sophie.store.backend.context.purchase.infrastructure.persistence.PurchaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PurchaseRepositoryJpaAdapter implements PurchaseRepository {

    private final PurchaseJpaRepository purchaseJpaRepository;
    private final PurchaseMapper mapper = new PurchaseMapper();

    @Override
    public List<Purchase> findAll() {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findAll();
        return mapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        Optional<PurchaseEntity> optPurchaseEntity = purchaseJpaRepository.findById(id);
        return optPurchaseEntity.map(mapper::entityToModel);
    }

    @Override
    public List<Purchase> findByUserId(Long userId) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findAllByUserId(userId);
        return mapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public List<Purchase> findByPaymentTypeId(Long paymentTypeId) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findAllByPaymentTypeId(paymentTypeId);
        return mapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public List<Purchase> findByDate(Timestamp date) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findAllByDate(date);
        return mapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public List<Purchase> findByDateBetween(Timestamp fromDate, Timestamp toDate) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findByDateBetween(fromDate, toDate);
        return mapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public List<Purchase> findByTotalBetween(BigDecimal fromTotal, BigDecimal toTotal) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findByTotalBetween(fromTotal, toTotal);
        return mapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public Purchase generate(Purchase purchase) {
        PurchaseEntity purchaseEntity = purchaseJpaRepository.save(mapper.modelToEntity(purchase));
        return mapper.entityToModel(purchaseEntity);
    }

    @Override
    public void deleteById(Long id) { purchaseJpaRepository.deleteById(id); }

}
