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
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AddPurchaseHasProductUseCase {

    private final Logger logger = Logger.getLogger(AddPurchaseHasProductUseCase.class.getName());

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public PurchaseHasProduct add(PurchaseHasProduct purchaseHasProduct) throws NoResultsException, InvalidBodyException {

        logger.info("ACCION ADD PURCHASE_HAS_PRODUCT -> Iniciando proceso con body: " + purchaseHasProduct.toString());

        Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseHasProduct.getId().getPurchaseId());
        if(optPurchase.isEmpty()) throw new NoResultsException(errorMessages.NO_PURCHASE_RESULTS);
        logger.info("ACCION ADD PURCHASE_HAS_PRODUCT -> Compra encontrada con éxito");

        Optional<Product> optProduct = productRepository.findById(purchaseHasProduct.getId().getProductId());
        if(optProduct.isEmpty()) throw new NoResultsException(errorMessages.NO_PRODUCT_RESULTS);
        logger.info("ACCION ADD PURCHASE_HAS_PRODUCT -> Producto encontrado con éxito");

        purchaseHasProduct.setPurchase(optPurchase.get());
        purchaseHasProduct.setProduct(optProduct.get());

        if(!purchaseHasProduct.isValid(purchaseHasProduct)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION ADD PURCHASE_HAS_PRODUCT -> Validé cuerpo de la petición");

        purchaseHasProduct.setUnitPrice(optProduct.get().getPrice());
        purchaseHasProduct.setSubtotal(optProduct.get().getPrice().multiply(BigDecimal.valueOf(purchaseHasProduct.getQuantity())));

        logger.info("ACCION ADD PURCHASE_HAS_PRODUCT -> Agregando producto a la compra");

        return purchaseHasProductRepository.add(purchaseHasProduct);
    }

}
