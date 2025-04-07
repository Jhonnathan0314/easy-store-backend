package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByPurchaseIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;

    public List<PurchaseHasProduct> findByPurchaseId(Long purchaseId) throws NoResultsException {

        log.info("ACCION FINDBYPURCHASEID PURCHASE_HAS_PRODUCT -> Iniciando búsqueda con id: {}", purchaseId);

        List<PurchaseHasProduct> purchaseHasProducts = purchaseHasProductRepository.findByPurchaseId(purchaseId);
        if(purchaseHasProducts == null || purchaseHasProducts.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYPURCHASEID PURCHASE_HAS_PRODUCT -> Encontré purchase_has_products con éxito");

        return purchaseHasProducts;
    }

}
