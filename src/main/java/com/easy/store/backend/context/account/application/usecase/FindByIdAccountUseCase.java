package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByIdAccountUseCase {

    private final Logger logger = Logger.getLogger(FindByIdAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account findById(Long id) throws NoResultsException {

        logger.info("ACCION FINDBYID ACCOUNT -> Iniciando busqueda con id: " + id);

        Optional<Account> accounts = accountRepository.findById(id);
        if(accounts.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID ACCOUNT -> Encontré cuenta con éxito");

        return accounts.get();
    }

}
