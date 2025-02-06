package com.easy.store.backend.context.payment_type.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreatePaymentTypeUseCase {

    private final PaymentTypeRepository paymentTypeRepository;
    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public PaymentType create(PaymentType paymentType) throws DuplicatedException, InvalidBodyException, NoIdReceivedException, NonExistenceException {

        if(!paymentType.isValid(paymentType)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(paymentType.getAccount().getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        Account account = accountRepository.findById(paymentType.getAccount().getId()).orElse(null);
        if(account == null) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        paymentType.setAccount(account);

        Optional<PaymentType> paymentTypeDb = paymentTypeRepository.findByNameAndAccountId(paymentType.getName(), paymentType.getAccount().getId());
        if(paymentTypeDb.isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return paymentTypeRepository.create(paymentType);
    }

}
