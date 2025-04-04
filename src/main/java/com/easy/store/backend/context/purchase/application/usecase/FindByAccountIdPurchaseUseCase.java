package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByAccountIdPurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindByAccountIdPurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final PurchaseHasProductRepository purchaseHasProductRepository;

    public List<Purchase> findByAccountId(Long accountId) throws NoResultsException {

        logger.info("ACCION FINDBYACCOUNTID PURCHASE -> Iniciando búsqueda con id: " + accountId);

        List<Purchase> purchases = purchaseRepository.findByAccountId(accountId);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYACCOUNTID PURCHASE -> Encontré compras con éxito");

        for (Purchase purchase : purchases) {
            purchase.setProducts(purchaseHasProductRepository.findByPurchaseId(purchase.getId()));
        }

        return purchases;
    }

}
