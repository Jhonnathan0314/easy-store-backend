package com.sophie.store.backend.context.purchase_has_product.application.usecase;

import com.sophie.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.sophie.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoveByIdPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public void removeById(Long id) throws NonExistenceException {

        Optional<PurchaseHasProduct> optPurchaseHasProduct = purchaseHasProductRepository.findById(id);
        if(optPurchaseHasProduct.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);

        purchaseHasProductRepository.removeById(id);
    }

}
