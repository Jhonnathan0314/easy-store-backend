package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindAllAccountUseCase {

    private final Logger logger = Logger.getLogger(FindAllAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<Account> findAll() throws NoResultsException {

        logger.info("ACCION FINDALL ACCOUNT -> Iniciando busqueda");

        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDALL ACCOUNT -> Encontré cuentas con éxito");

        return accounts;
    }

}
