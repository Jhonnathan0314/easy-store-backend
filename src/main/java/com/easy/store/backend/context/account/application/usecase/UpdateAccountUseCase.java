package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAccountUseCase {

    private final AccountRepository accountRepository;

    public Account update(Account account) throws InvalidBodyException, NoChangesException,
            NoIdReceivedException, NonExistenceException {

        log.info("ACCION UPDATE ACCOUNT -> Iniciando actualización con body: {}", account.toString());

        if(account.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UPDATE ACCOUNT -> Id validado con éxito");

        if(!account.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UPDATE ACCOUNT -> Body validado con éxito");

        Optional<Account> accountIdOpt = accountRepository.findById(account.getId());

        if(accountIdOpt.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION UPDATE ACCOUNT -> Validación cuenta existente con éxito");

        if(account.equals(accountIdOpt.get())) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        log.info("ACCION UPDATE ACCOUNT -> Validación cambios a aplicar con éxito");

        if(account.getImageName() == null || account.getImageName().isEmpty()) account.setImageName("store.png");

        log.info("ACCION UPDATE ACCOUNT -> Actualizando cuenta");

        return accountRepository.update(account);
    }

}
