package com.easy.store.backend.context.account.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdAccountUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdAccountUseCase.class.getName());

    private final AccountRepository accountRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID ACCOUNT -> Iniciando eliminado con id: " + id);

        Optional<Account> accounts = accountRepository.findById(id);
        if(accounts.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID ACCOUNT -> Encontré cuenta con éxito");

        logger.info("ACCION DELETEBYID ACCOUNT -> Eliminando cuenta");

        accountRepository.deleteById(id);
    }

}
