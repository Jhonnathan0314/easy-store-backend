package com.sophie.store.backend.context.purchase_has_product.application.usecase;

import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PurchaseHasProduct findById(Long id) throws NoResultsException {
        Optional<PurchaseHasProduct> optPurchaseHasProducts = purchaseHasProductRepository.findById(id);
        if(optPurchaseHasProducts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return optPurchaseHasProducts.get();
    }

}
