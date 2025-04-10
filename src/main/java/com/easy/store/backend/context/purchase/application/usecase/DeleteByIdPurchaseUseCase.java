package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidActionException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdPurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseHasProductRepository purchaseHasProductRepository;

    public void deleteById(Long id) throws NonExistenceException, InvalidActionException {

        log.info("ACCION DELETEBYID PURCHASE -> Iniciando proceso con id: {}", id);

        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if(purchase.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID PURCHASE -> Compra encontrada con éxito");

        List<PurchaseHasProduct> hasProducts = purchaseHasProductRepository.findByPurchaseId(purchase.get().getId());
        if(!hasProducts.isEmpty()) throw new InvalidActionException(ErrorMessages.INVALID_ACTION);

        log.info("ACCION DELETEBYID PURCHASE -> Eliminando compra");

        purchaseRepository.deleteById(id);
    }

}
