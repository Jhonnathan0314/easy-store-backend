package com.easy.store.backend.context.purchase_has_product.infrastructure.adapter;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductAddMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductResponseMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.mappers.PurchaseHasProductUpdateMapper;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PurchaseHasProductRepositoryJpaAdapter implements PurchaseHasProductRepository {

    private final PurchaseHasProductJpaRepository purchaseHasProductJpaRepository;
    private final PurchaseHasProductAddMapper addMapper = new PurchaseHasProductAddMapper();
    private final PurchaseHasProductUpdateMapper updateMapper = new PurchaseHasProductUpdateMapper();
    private final PurchaseHasProductResponseMapper responseMapper = new PurchaseHasProductResponseMapper();

    @Override
    public Optional<PurchaseHasProduct> findByPurchaseIdAndProductId(PurchaseHasProductId id) {
        Optional<PurchaseHasProductEntity> optPurchaseHasProduct = purchaseHasProductJpaRepository.findByPurchaseIdAndProductId(id.getPurchaseId(), id.getProductId());
        return optPurchaseHasProduct.map(responseMapper::entityToModel);
    }

    @Override
    public List<PurchaseHasProduct> findByPurchaseId(Long purchaseId) {
        List<PurchaseHasProductEntity> purchaseHasProducts = purchaseHasProductJpaRepository.findByPurchaseId(purchaseId);
        return responseMapper.entitiesToModels(purchaseHasProducts);
    }

    @Override
    public PurchaseHasProduct add(PurchaseHasProduct purchaseHasProduct) {
        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductJpaRepository.save(addMapper.modelToEntity(purchaseHasProduct));
        return responseMapper.entityToModel(purchaseHasProductEntity);
    }

    @Override
    public List<PurchaseHasProduct> addAll(List<PurchaseHasProduct> purchaseHasProducts) {
        List<PurchaseHasProductEntity> purchaseHasProductEntities = purchaseHasProductJpaRepository.saveAll(addMapper.modelsToEntities(purchaseHasProducts));
        return responseMapper.entitiesToModels(purchaseHasProductEntities);
    }

    @Override
    public PurchaseHasProduct update(PurchaseHasProduct purchaseHasProduct) {
        PurchaseHasProductEntity purchaseHasProductEntity = purchaseHasProductJpaRepository.save(updateMapper.modelToEntity(purchaseHasProduct));
        return responseMapper.entityToModel(purchaseHasProductEntity);
    }

    @Override
    public void removeByPurchaseIdAndProductId(PurchaseHasProductId id) {
        purchaseHasProductJpaRepository.deleteById(id);
    }

    @Override
    public void removeAll(List<PurchaseHasProductId> ids) {
        purchaseHasProductJpaRepository.deleteAllByIdInBatch(ids);
    }

}
