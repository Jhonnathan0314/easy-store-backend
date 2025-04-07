package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindAllAccountUseCase {

    private final AccountRepository accountRepository;

    public List<Account> findAll() throws NoResultsException {

        log.info("ACCION FINDALL ACCOUNT -> Iniciando busqueda");

        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL ACCOUNT -> Encontré cuentas con éxito");

        return accounts;
    }

}
