package com.sophie.store.backend.context.purchase.application.usecase;

import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByDatePurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Purchase> findByDate(Timestamp date) throws NoResultsException {
        List<Purchase> purchases = purchaseRepository.findByDate(date);
        if(purchases == null || purchases.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return purchases;
    }

}
