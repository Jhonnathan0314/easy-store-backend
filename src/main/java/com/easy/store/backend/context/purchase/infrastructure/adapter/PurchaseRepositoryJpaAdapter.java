package com.easy.store.backend.context.purchase.infrastructure.adapter;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseGenerateMapper;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseResponseMapper;
import com.easy.store.backend.context.purchase.infrastructure.mappers.PurchaseUpdateMapper;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseJpaRepository;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PurchaseRepositoryJpaAdapter implements PurchaseRepository {

    private final PurchaseJpaRepository purchaseJpaRepository;
    private final PurchaseHasProductJpaRepository purchaseHasProductJpaRepository;
    private final PurchaseGenerateMapper generateMapper = new PurchaseGenerateMapper();
    private final PurchaseUpdateMapper updateMapper = new PurchaseUpdateMapper();
    private final PurchaseResponseMapper responseMapper = new PurchaseResponseMapper();

    @Override
    public List<Purchase> findAll() {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findAll();
        return responseMapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public Optional<Purchase> findById(Long id) {
        Optional<PurchaseEntity> optPurchaseEntity = purchaseJpaRepository.findById(id);
        return optPurchaseEntity.map(responseMapper::entityToModel);
    }

    @Override
    public List<Purchase> findByAccountId(Long accountId) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findByAccountId(accountId);
        return responseMapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public List<Purchase> findByCategoryId(Long categoryId) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findByCategoryId(categoryId);
        return responseMapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public List<Purchase> findByUserId(Long userId) {
        List<PurchaseEntity> purchaseEntities = purchaseJpaRepository.findByUserId(userId);
        return responseMapper.entitiesToModels(purchaseEntities);
    }

    @Override
    public Purchase generate(Purchase purchase) {
        PurchaseEntity purchaseEntity = purchaseJpaRepository.save(generateMapper.modelToEntity(purchase));
        return responseMapper.entityToModel(purchaseEntity);
    }

    @Override
    public Purchase update(Purchase purchase) {
        PurchaseEntity purchaseEntity = purchaseJpaRepository.save(updateMapper.modelToEntity(purchase));
        return responseMapper.entityToModel(purchaseEntity);
    }

    @Override
    public Purchase recalculateTotal(Long purchaseId) {
        PurchaseEntity purchaseEntity = purchaseJpaRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found with id: " + purchaseId));

        BigDecimal total = purchaseHasProductJpaRepository.findByPurchaseId(purchaseId).stream()
                .map(PurchaseHasProductEntity::getSubtotal)
                .filter(subtotal -> subtotal != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchaseEntity.setTotal(total);
        PurchaseEntity saved = purchaseJpaRepository.save(purchaseEntity);
        return responseMapper.entityToModel(saved);
    }

    @Override
    public void deleteById(Long id) { purchaseJpaRepository.deleteById(id); }

}
