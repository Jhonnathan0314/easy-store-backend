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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddAllPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PurchaseHasProduct> addAll(List<PurchaseHasProduct> purchaseHasProducts) throws NoResultsException, InvalidBodyException {
        for (PurchaseHasProduct purchaseHasProduct : purchaseHasProducts) {
            Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseHasProduct.getId().getPurchaseId());
            if (optPurchase.isEmpty()) throw new NoResultsException(errorMessages.NO_PURCHASE_RESULTS);

            Optional<Product> optProduct = productRepository.findById(purchaseHasProduct.getId().getProductId());
            if (optProduct.isEmpty()) throw new NoResultsException(errorMessages.NO_PRODUCT_RESULTS);

            purchaseHasProduct.setPurchase(optPurchase.get());
            purchaseHasProduct.setProduct(optProduct.get());

            if (!purchaseHasProduct.isValid(purchaseHasProduct))
                throw new InvalidBodyException(errorMessages.INVALID_BODY);

            purchaseHasProduct.setUnitPrice(optProduct.get().getPrice());
            purchaseHasProduct.setSubtotal(optProduct.get().getPrice().multiply(BigDecimal.valueOf(purchaseHasProduct.getQuantity())));
        }
        return purchaseHasProductRepository.addAll(purchaseHasProducts);
    }

}
