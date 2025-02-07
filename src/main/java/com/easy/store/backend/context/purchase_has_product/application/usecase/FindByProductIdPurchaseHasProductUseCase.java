package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByProductIdPurchaseHasProductUseCase {

    private final Logger logger = Logger.getLogger(FindByProductIdPurchaseHasProductUseCase.class.getName());

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<PurchaseHasProduct> findByProductId(Long productId) throws NoResultsException {

        logger.info("ACCION FINDBYPRODUCTID PURCHASE_HAS_PRODUCT -> Iniciando búsqueda con id: " + productId);

        List<PurchaseHasProduct> purchaseHasProducts = purchaseHasProductRepository.findByProductId(productId);
        if(purchaseHasProducts == null || purchaseHasProducts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYPRODUCTID PURCHASE_HAS_PRODUCT -> Encontré purchase_has_products con éxito");

        return purchaseHasProducts;
    }

}
