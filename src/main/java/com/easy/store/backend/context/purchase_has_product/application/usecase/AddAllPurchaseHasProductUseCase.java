package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddAllPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PurchaseHasProduct> addAll(List<PurchaseHasProduct> purchaseHasProducts, Long purchaseId, List<Long> productIds) throws NoResultsException, InvalidBodyException {
        if(purchaseHasProducts.size() != productIds.size()) throw new InvalidBodyException(errorMessages.NO_SIZE_EQUALS);

        for (int i = 0; i < purchaseHasProducts.size(); i++) {
            Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseId);
            if (optPurchase.isEmpty()) throw new NoResultsException(errorMessages.NO_PURCHASE_RESULTS);

            Optional<Product> optProduct = productRepository.findById(productIds.get(i));
            if (optProduct.isEmpty()) throw new NoResultsException(errorMessages.NO_PRODUCT_RESULTS);

            purchaseHasProducts.get(i).setPurchase(optPurchase.get());
            purchaseHasProducts.get(i).setProduct(optProduct.get());

            if (!purchaseHasProducts.get(i).isValid(purchaseHasProducts.get(i)))
                throw new InvalidBodyException(errorMessages.INVALID_BODY);
        }
        return purchaseHasProductRepository.addAll(purchaseHasProducts);
    }

}
