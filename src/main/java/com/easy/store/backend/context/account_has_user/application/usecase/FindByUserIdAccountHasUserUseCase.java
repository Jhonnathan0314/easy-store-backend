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
public class FindByUserIdAccountHasUserUseCase {

    private final Logger logger = Logger.getLogger(FindByUserIdAccountHasUserUseCase.class.getName());

    private final AccountHasUserRepository accountHasUserRepository;

    public List<AccountHasUser> findByUserId(Long idUser) throws NoResultsException {

        logger.info("ACCION FINDBYUSERID ACCOUNT_HAS_USER -> Iniciando búsqueda con idUser: " + idUser);

        List<AccountHasUser> accountHasUsers = accountHasUserRepository.findByUserId(idUser);
        if(accountHasUsers.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYUSERID ACCOUNT_HAS_USER -> Encontré cuenta tiene usuario con éxito");

        return accountHasUsers;
    }

}
