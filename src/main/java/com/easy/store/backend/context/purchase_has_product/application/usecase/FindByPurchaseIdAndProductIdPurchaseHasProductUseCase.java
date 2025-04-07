package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByPurchaseIdAndProductIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;

    public PurchaseHasProduct findByPurchaseIdAndProductId(PurchaseHasProductId id) throws NoResultsException {

        log.info("ACCION FINDBYPURCHASEIDANDPRODUCTID PURCHASE_HAS_PRODUCT -> Iniciando búsqueda con id: {}", id.toString());

        Optional<PurchaseHasProduct> purchaseHasProducts = purchaseHasProductRepository.findByPurchaseIdAndProductId(id);
        if(purchaseHasProducts.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYPURCHASEIDANDPRODUCTID PURCHASE_HAS_PRODUCT -> Encontré purchase_has_product con éxito");

        return purchaseHasProducts.get();
    }

}
