package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdAccountUseCase {

    private final AccountRepository accountRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID ACCOUNT -> Iniciando eliminado con id: {}", id);

        Optional<Account> accounts = accountRepository.findById(id);
        if(accounts.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID ACCOUNT -> Encontré cuenta con éxito");

        log.info("ACCION DELETEBYID ACCOUNT -> Eliminando cuenta");

        accountRepository.deleteById(id);
    }

}
