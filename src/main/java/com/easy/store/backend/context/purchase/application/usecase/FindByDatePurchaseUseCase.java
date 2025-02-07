package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByDatePurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindByDatePurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findByCreationDate(Timestamp creationDate) throws NoResultsException {

        logger.info("ACCION FINDBYCREATIONDATE PURCHASE -> Iniciando búsqueda con fecha: " + creationDate);

        List<Purchase> purchases = purchaseRepository.findByCreationDate(creationDate);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYCREATIONDATE PURCHASE -> Encontré compras con éxito");

        return purchases;
    }

}
