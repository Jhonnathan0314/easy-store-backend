package com.sophie.store.backend.context.purchase.application.usecase;

import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByIdPurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Purchase findById(Long id) throws NoResultsException {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        if(optionalPurchase.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return optionalPurchase.get();
    }

}
