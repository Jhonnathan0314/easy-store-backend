package com.sophie.store.backend.context.purchase.application.usecase;

import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteByIdPurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExisteceException {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if(purchase.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        purchaseRepository.deleteById(id);
    }

}
