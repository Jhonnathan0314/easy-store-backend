package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByProductIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PurchaseHasProduct> findByProductId(Long productId) throws NoResultsException {
        List<PurchaseHasProduct> purchaseHasProducts = purchaseHasProductRepository.findByProductId(productId);
        if(purchaseHasProducts == null || purchaseHasProducts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return purchaseHasProducts;
    }

}
