package com.easy.store.backend.context.account_has_user.application.usecase;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdAccountHasUserUseCase {

    private final AccountHasUserRepository accountHasUserRepository;

    public void deleteById(AccountHasUserId id) throws NonExistenceException {

        log.info("ACCION CREATE ACCOUNT_HAS_USER -> Iniciando creación con id: {}", id.toString());

        Optional<AccountHasUser> accountHasUsers = accountHasUserRepository.findById(id);
        if(accountHasUsers.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID ACCOUNT_HAS_ROLE -> Encontre cuenta tiene usuario con exito");

        log.info("ACCION CREATE ACCOUNT_HAS_USER -> Eliminando account_has_user");

        accountHasUserRepository.deleteById(id);
    }

}
