package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.domain.port.ProductRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public PurchaseHasProduct update(PurchaseHasProduct purchaseHasProduct) throws NoResultsException, InvalidBodyException, NoChangesException, NonExistenceException {

        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Inicia el proceso con body: {}", purchaseHasProduct.toString());

        if(!purchaseHasProduct.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Validé cuerpo de la petición");

        Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseHasProduct.getId().getPurchaseId());
        if(optPurchase.isEmpty()) throw new NoResultsException(ErrorMessages.NO_PURCHASE_RESULTS);
        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Validé existencia de la compra");

        Optional<Product> optProduct = productRepository.findById(purchaseHasProduct.getId().getProductId());
        if(optProduct.isEmpty()) throw new NoResultsException(ErrorMessages.NO_PRODUCT_RESULTS);
        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Validé existencia del producto");

        PurchaseHasProductId id = PurchaseHasProductId.builder()
                .purchaseId(optPurchase.get().getId())
                .productId(optProduct.get().getId())
                .build();

        Optional<PurchaseHasProduct> optPurchaseHasProduct = purchaseHasProductRepository.findByPurchaseIdAndProductId(id);
        if(optPurchaseHasProduct.isEmpty()) throw new NoResultsException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Validé existencia del objeto purchase_has_product");

        if(!areDifferences(optPurchaseHasProduct.get(), purchaseHasProduct)) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Validé que hayan cambios a aplicar");

        if(optProduct.get().getQuantity() < purchaseHasProduct.getQuantity()) throw new NonExistenceException(ErrorMessages.NO_STOCK);

        purchaseHasProduct.setPurchase(optPurchase.get());
        purchaseHasProduct.setProduct(optProduct.get());

        purchaseHasProduct.setUnitPrice(optProduct.get().getPrice());
        purchaseHasProduct.setSubtotal(optProduct.get().getPrice().multiply(BigDecimal.valueOf(purchaseHasProduct.getQuantity())));

        log.info("ACCION UDPATE PURCHASE_HAS_PRODUCT -> Actualizando objeto purchase_has_product");

        return purchaseHasProductRepository.add(purchaseHasProduct);
    }

    private boolean areDifferences(PurchaseHasProduct purchaseHasProductDb, PurchaseHasProduct purchaseHasProduct) {
        return !purchaseHasProductDb.getQuantity().equals(purchaseHasProduct.getQuantity());
    }

}
