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
public class FindByIdUserUseCase {

    private final Logger logger = Logger.getLogger(FindByIdUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public User findById(Long id) throws NoResultsException {

        logger.info("ACCION FINDBYID USER -> Iniciando búsqueda con id: " + id);

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID USER -> Encontré usuario con éxito");

        return optionalUser.get();
    }

}
