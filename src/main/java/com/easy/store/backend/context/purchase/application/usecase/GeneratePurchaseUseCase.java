package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneratePurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Purchase generate(Purchase purchase, Long userId, Long paymentTypeId) throws InvalidBodyException, NoResultsException {

        Optional<User> optUser = userRepository.findById(userId);
        if(optUser.isEmpty()) throw new NoResultsException(errorMessages.NO_USER_RESULTS);

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(paymentTypeId);
        if(optPaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_PAYMENT_TYPE_RESULTS);

        purchase.setUser(optUser.get());
        purchase.setPaymentType(optPaymentType.get());

        if(!purchase.isValid(purchase)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        return purchaseRepository.generate(purchase);
    }

}
