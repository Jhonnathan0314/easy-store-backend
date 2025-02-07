package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByCategoryIdPurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindByCategoryIdPurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findByCategoryId(Long categoryId) throws NoResultsException {

        logger.info("ACCION FINDBYCATEGORYID PURCHASE -> Iniciando búsqueda con id: " + categoryId);

        List<Purchase> purchases = purchaseRepository.findByCategoryId(categoryId);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYCATEGORYID PURCHASE -> Encontré compras con éxito");

        return purchases;
    }

}
