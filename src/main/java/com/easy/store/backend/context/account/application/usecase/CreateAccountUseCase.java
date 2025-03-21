package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final Logger logger = Logger.getLogger(CreateAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Account create(Account account) throws InvalidBodyException {

        logger.info("ACCION CREATE ACCOUNT -> Iniciando creación con body: " + account.toString());

        if(!account.isValid(account)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE ACCOUNT -> Body validado con éxito");

        if(account.getImageName() == null || account.getImageName().isEmpty()) account.setImageName("store.png");

        logger.info("ACCION CREATE ACCOUNT -> Creando cuenta");

        return accountRepository.create(account);
    }

}
