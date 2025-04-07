package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;

    public Account create(Account account) throws InvalidBodyException {

        log.info("ACCION CREATE ACCOUNT -> Iniciando creación con body: {}", account.toString());

        if(!account.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE ACCOUNT -> Body validado con éxito");

        if(account.getImageName() == null || account.getImageName().isEmpty()) account.setImageName("store.png");

        log.info("ACCION CREATE ACCOUNT -> Creando cuenta");

        return accountRepository.create(account);
    }

}
