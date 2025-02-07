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
public class FindByPaymentTypeIdPurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindByPaymentTypeIdPurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findByPaymentTypeId(Long paymentTypeId) throws NoResultsException {

        logger.info("ACCION FINDBYPAYMENTTYPEID PURCHASE -> Iniciando búsqueda con id: " + paymentTypeId);

        List<Purchase> purchases = purchaseRepository.findByPaymentTypeId(paymentTypeId);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYPAYMENTTYPEID PURCHASE -> Encontré compras con éxito");

        return purchases;
    }

}
