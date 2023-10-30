package com.sophie.store.backend.context.purchase_has_product.application.usecase;

import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllByProductIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PurchaseHasProduct> findAllByProductId(Long productId) throws NoResultsException {
        List<PurchaseHasProduct> purchaseHasProducts = purchaseHasProductRepository.findAllByProductId(productId);
        if(purchaseHasProducts == null || purchaseHasProducts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return purchaseHasProducts;
    }

}
