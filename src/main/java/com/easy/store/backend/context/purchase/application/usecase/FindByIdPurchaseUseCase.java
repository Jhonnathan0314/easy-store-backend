package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByIdPurchaseUseCase {

    private final Logger logger = Logger.getLogger(FindByIdPurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Purchase findById(Long id) throws NoResultsException {

        logger.info("ACCION FINDBYID PURCHASE -> Iniciando búsqueda con id: " + id);

        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        if(optionalPurchase.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID PURCHASE -> Encontré compra con éxito");

        return optionalPurchase.get();
    }

}
