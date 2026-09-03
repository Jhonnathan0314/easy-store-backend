package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveAllPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final PurchaseRepository purchaseRepository;

    public void removeAll(List<PurchaseHasProductId> ids) throws NoIdReceivedException {

        log.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Iniciando eliminado con {} ids", ids.size());

        if(ids.isEmpty()) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Validé ids recibido");

        log.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Inicia eliminado de objetos");

        purchaseHasProductRepository.removeAll(ids);

        ids.stream()
                .map(PurchaseHasProductId::getPurchaseId)
                .distinct()
                .forEach(purchaseRepository::recalculateTotal);
        log.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Total de las compras recalculado");
    }

}
