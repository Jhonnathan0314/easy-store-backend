package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindAllUserUseCase {

    private final Logger logger = Logger.getLogger(FindAllUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<User> findAll() throws NoResultsException {

        logger.info("ACCION FINDALL USER -> Iniciando búsqueda");

        List<User> users = userRepository.findAll();
        if(users == null || users.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDALL USER -> Encontré usuarios con éxito");

        return users;
    }

}
