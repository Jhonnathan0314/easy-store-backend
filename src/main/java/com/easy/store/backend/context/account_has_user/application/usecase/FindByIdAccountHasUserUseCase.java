package com.easy.store.backend.context.account_has_user.application.usecase;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByIdAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(FindByIdAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;

    public AccountHasUser findById(AccountHasUserId id) throws NoResultsException {

        logger.info("ACCION FINDBYID ACCOUNT_HAS_USER -> Iniciando búsqueda con idAccount: " + id.toString());

        Optional<AccountHasUser> accountHasUsers = accountHasUserRepository.findById(id);
        if(accountHasUsers.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID ACCOUNT_HAS_USER -> Encontré cuenta tiene usuario con éxito");

        return accountHasUsers.get();
    }

}
