package com.easy.store.backend.context.account_has_user.application.usecase;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByAccountIdAccountHasUserUseCase {

    private final AccountHasUserRepository accountHasUserRepository;

    public List<AccountHasUser> findByAccountId(Long idAccount) throws NoResultsException {

        log.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_USER -> Iniciando busqueda con idAccount: {}", idAccount);

        List<AccountHasUser> accountHasUsers = accountHasUserRepository.findByAccountId(idAccount);
        if(accountHasUsers.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_USER -> Encontré cuenta tiene usuario con éxito");

        return accountHasUsers;
    }

}
