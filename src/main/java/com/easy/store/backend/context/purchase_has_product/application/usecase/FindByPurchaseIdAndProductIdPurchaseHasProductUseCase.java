package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByPurchaseIdAndProductIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PurchaseHasProduct findByPurchaseIdAndProductId(PurchaseHasProductId id) throws NoResultsException {
        Optional<PurchaseHasProduct> purchaseHasProducts = purchaseHasProductRepository.findByPurchaseIdAndProductId(id);
        if(purchaseHasProducts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return purchaseHasProducts.get();
    }

}
