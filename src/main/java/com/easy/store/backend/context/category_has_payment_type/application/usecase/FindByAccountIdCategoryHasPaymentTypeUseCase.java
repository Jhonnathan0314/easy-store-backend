package com.easy.store.backend.context.category_has_payment_type.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindByAccountIdCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final AccountRepository accountRepository;

    public List<CategoryHasPaymentType> findByAccountId(Long accountId) throws NoResultsException {

        log.info("ACCION FINDBYACCOUNTID CATEGORY HAS PAYMENT TYPE -> Iniciando busqueda");

        Optional<Account> optAccount = accountRepository.findById(accountId);
        if(optAccount.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYACCOUNTID CATEGORY HAS PAYMENT TYPE -> Validé cuenta existente");

        List<CategoryHasPaymentType> categoryPaymentTypes = categoryHasPaymentTypeRepository.findByAccountId(accountId);
        if(categoryPaymentTypes.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        log.info("ACCION FINDBYACCOUNTID CATEGORY HAS PAYMENT TYPE -> Encontré con éxito");

        return categoryPaymentTypes;
    }

}
