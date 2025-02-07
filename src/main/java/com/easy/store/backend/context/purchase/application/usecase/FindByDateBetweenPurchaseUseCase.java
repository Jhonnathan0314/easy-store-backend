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
public class FindByDateBetweenPurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindByDateBetweenPurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findByCreationDateBetween(Timestamp fromDate, Timestamp toDate) throws NoResultsException {

        logger.info("ACCION FINDBYCREATIONDATEBETWEEN PURCHASE -> " +
                "Iniciando búsqueda con fechas from: " + fromDate + " to " + toDate);

        List<Purchase> purchases = purchaseRepository.findByCreationDateBetween(fromDate, toDate);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYCREATIONDATEBETWEEN PURCHASE -> Encontré compras con éxito");

        return purchases;
    }
}
