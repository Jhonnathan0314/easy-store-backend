package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByAccountIdUserUseCase {

    private final Logger logger = Logger.getLogger(FindByAccountIdUserUseCase.class.getName());

    private final UserRepository userRepository;

    public List<User> findByAccountId(Long accountId) throws NoResultsException {

        logger.info("ACCION FINDBYACCOUNTID USER -> Iniciando búsqueda con id: " + accountId);

        List<User> users = userRepository.findByAccountId(accountId);
        if(users.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYACCOUNTID USER -> Encontré usuarios con éxito");

        return users;
    }

}
