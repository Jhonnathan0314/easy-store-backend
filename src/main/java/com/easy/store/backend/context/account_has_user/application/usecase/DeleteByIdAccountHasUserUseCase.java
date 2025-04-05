package com.easy.store.backend.context.account_has_user.application.usecase;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;

    public void deleteById(AccountHasUserId id) throws NonExistenceException {

        logger.info("ACCION CREATE ACCOUNT_HAS_USER -> Iniciando creación con id: " + id.toString());

        Optional<AccountHasUser> accountHasUsers = accountHasUserRepository.findById(id);
        if(accountHasUsers.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID ACCOUNT_HAS_ROLE -> Encontre cuenta tiene usuario con exito");

        logger.info("ACCION CREATE ACCOUNT_HAS_USER -> Eliminando account_has_user");

        accountHasUserRepository.deleteById(id);
    }

}
