package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RemoveByIdPurchaseHasProductUseCase {

    private final Logger logger = Logger.getLogger(RemoveByIdPurchaseHasProductUseCase.class.getName());

    private final PurchaseHasProductRepository purchaseHasProductRepository;

    public void removeByPurchaseIdAndProductId(PurchaseHasProductId id) throws NonExistenceException {

        logger.info("ACCION REMOVEBYPURCHASEIDANDPRODUCTID PURCHASE_HAS_PRODUCT -> Iniciando eliminado con id: " + id.toString());

        Optional<PurchaseHasProduct> optPurchaseHasProduct = purchaseHasProductRepository.findByPurchaseIdAndProductId(id);
        if(optPurchaseHasProduct.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION REMOVEBYPURCHASEIDANDPRODUCTID PURCHASE_HAS_PRODUCT -> Validé existencia con éxito");

        logger.info("ACCION REMOVEBYPURCHASEIDANDPRODUCTID PURCHASE_HAS_PRODUCT -> Inicia eliminado");

        purchaseHasProductRepository.removeByPurchaseIdAndProductId(id);
    }

}
