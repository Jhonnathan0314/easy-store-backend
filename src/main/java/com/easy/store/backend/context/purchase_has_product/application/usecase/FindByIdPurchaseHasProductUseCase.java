package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
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
