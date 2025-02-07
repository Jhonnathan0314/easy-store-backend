package com.easy.store.backend.context.purchase_has_product.application.usecase;

import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
import com.easy.store.backend.context.purchase_has_product.domain.port.PurchaseHasProductRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoveAllPurchaseHasProductUseCase {

    private final PurchaseHasProductRepository purchaseHasProductRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void removeAll(List<PurchaseHasProductId> ids) throws NoIdReceivedException {
        if(ids.isEmpty()) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        purchaseHasProductRepository.removeAll(ids);
    }

}
