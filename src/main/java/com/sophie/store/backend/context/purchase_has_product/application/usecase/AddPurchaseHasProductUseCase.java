package com.sophie.store.backend.context.purchase_has_product.application.usecase;

import com.sophie.store.backend.context.product.domain.model.Product;
import com.sophie.store.backend.context.product.domain.port.ProductRepository;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public PurchaseHasProduct add(PurchaseHasProduct purchaseHasProduct, Long purchaseId, Long productId) throws NoResultsException, InvalidBodyException {
        Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseId);
        if(optPurchase.isEmpty()) throw new NoResultsException(errorMessages.NO_PURCHASE_RESULTS);

        Optional<Product> optProduct = productRepository.findById(productId);
        if(optProduct.isEmpty()) throw new NoResultsException(errorMessages.NO_PRODUCT_RESULTS);

        purchaseHasProduct.setPurchase(optPurchase.get());
        purchaseHasProduct.setProduct(optProduct.get());

        if(!purchaseHasProduct.isValid(purchaseHasProduct)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        return purchaseHasProductRepository.add(purchaseHasProduct);
    }

}
