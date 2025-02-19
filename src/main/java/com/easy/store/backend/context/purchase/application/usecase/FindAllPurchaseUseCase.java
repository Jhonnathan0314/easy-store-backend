package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindAllPurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindAllPurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findAll() throws NoResultsException {

        logger.info("ACCION FINDALL PURCHASE -> Iniciando búsqueda");

        List<Purchase> purchases = purchaseRepository.findAll();
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDALL PURCHASE -> Encontré compras con éxito");

        for (Purchase purchase : purchases) {
            purchase.setProducts(purchaseHasProductRepository.findByPurchaseId(purchase.getId()));
        }

        return purchases;
    }
}
