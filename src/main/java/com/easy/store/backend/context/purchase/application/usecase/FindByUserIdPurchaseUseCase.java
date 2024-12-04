package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByUserIdPurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findByUserId(Long userId) throws NoResultsException {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return purchases;
    }

}