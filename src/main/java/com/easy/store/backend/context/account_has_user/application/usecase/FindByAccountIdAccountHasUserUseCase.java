package com.easy.store.backend.context.account_has_user.application.usecase;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByAccountIdAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(FindByAccountIdAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;

    public List<AccountHasUser> findByAccountId(Long idAccount) throws NoResultsException {

        logger.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_USER -> Iniciando busqueda con idAccount: " + idAccount);

        List<AccountHasUser> accountHasUsers = accountHasUserRepository.findByAccountId(idAccount);
        if(accountHasUsers.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_USER -> Encontré cuenta tiene usuario con éxito");

        return accountHasUsers;
    }

}
