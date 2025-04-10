package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
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
public class FindByIdPurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseHasProductRepository purchaseHasProductRepository;

    public Purchase findById(Long id) throws NoResultsException {

        log.info("ACCION FINDBYID PURCHASE -> Iniciando búsqueda con id: {}", id);

        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        if(optionalPurchase.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID PURCHASE -> Encontré compra con éxito");

        Purchase purchase = optionalPurchase.get();
        purchase.setProducts(purchaseHasProductRepository.findByPurchaseId(purchase.getId()));

        return purchase;
    }

}
