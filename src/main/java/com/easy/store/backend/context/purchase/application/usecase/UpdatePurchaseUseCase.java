package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdatePurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Purchase update(Purchase purchase) throws NoIdReceivedException, InvalidBodyException, NoResultsException, NoChangesException {

        if(purchase.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        Optional<Purchase> optPurchase = purchaseRepository.findById(purchase.getId());
        if(optPurchase.isEmpty()) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!purchase.isValid(purchase)) throw new NoResultsException(errorMessages.NO_PURCHASE_RESULTS);

        Optional<User> optUser = userRepository.findById(purchase.getUser().getId());
        if(optUser.isEmpty()) throw new NoResultsException(errorMessages.NO_USER_RESULTS);

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(purchase.getPaymentType().getId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_PAYMENT_TYPE_RESULTS);

        if(!areDifferences(optPurchase.get(), purchase)) throw new NoChangesException(errorMessages.NO_CHANGES);

        purchase.setTotal(optPurchase.get().getTotal());
        purchase.setUser(optUser.get());
        purchase.setPaymentType(optPaymentType.get());

        return purchaseRepository.update(purchase);
    }

    private boolean areDifferences(Purchase purchaseDb, Purchase purchase) {
        return !purchaseDb.getState().equals(purchase.getState()) ||
                !purchaseDb.getUser().getId().equals(purchase.getUser().getId()) ||
                !purchaseDb.getPaymentType().getId().equals(purchase.getPaymentType().getId());
    }

}
