package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdAccountUseCase {

    private final AccountRepository accountRepository;

    public Account findById(Long id) throws NoResultsException {

        log.info("ACCION FINDBYID ACCOUNT -> Iniciando busqueda con id: {}", id);

        Optional<Account> accounts = accountRepository.findById(id);
        if(accounts.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID ACCOUNT -> Encontré cuenta con éxito");

        return accounts.get();
    }

}
