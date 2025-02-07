package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateAccountUseCase {

    private final Logger logger = Logger.getLogger(UpdateAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account update(Account account) throws InvalidBodyException, NoChangesException,
            NoIdReceivedException, NonExistenceException {

        logger.info("ACCION UPDATE ACCOUNT -> Iniciando actualización con body: " + account.toString());

        if(account.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UPDATE ACCOUNT -> Id validado con éxito");

        if(!account.isValid(account)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UPDATE ACCOUNT -> Body validado con éxito");

        Optional<Account> accountIdOpt = accountRepository.findById(account.getId());

        if(accountIdOpt.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UPDATE ACCOUNT -> Validación cuenta existente con éxito");

        if(account.equals(accountIdOpt.get())) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UPDATE ACCOUNT -> Validación cambios a aplicar con éxito");

        logger.info("ACCION UPDATE ACCOUNT -> Actualizando cuenta");

        return accountRepository.update(account);
    }

}
