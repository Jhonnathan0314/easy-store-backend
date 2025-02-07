package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RemoveAllPurchaseHasProductUseCase {

    private final Logger logger = Logger.getLogger(RemoveAllPurchaseHasProductUseCase.class.getName());

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void removeAll(List<PurchaseHasProductId> ids) throws NoIdReceivedException {

        logger.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Iniciando eliminado con " + ids.size() + " ids");

        if(ids.isEmpty()) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Validé ids recibido");

        logger.info("ACCION REMOVEALL PURCHASE_HAS_PRODUCT -> Inicia eliminado de objetos");

        purchaseHasProductRepository.removeAll(ids);
    }

}
