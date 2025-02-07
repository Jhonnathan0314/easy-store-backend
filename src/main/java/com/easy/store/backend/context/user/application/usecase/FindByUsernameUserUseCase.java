package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByUsernameUserUseCase {

    private final Logger logger = Logger.getLogger(FindByUsernameUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Optional<User> findByUsername(String username) throws NoResultsException {

        logger.info("ACCION FINDBYUSERNAME USER -> Iniciando búsqueda");

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYUSERNAME USER -> Encontré usuario con éxito");

        return optionalUser;
    }

}
