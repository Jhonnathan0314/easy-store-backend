package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdUserUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID USER -> Iniciando proceso con id: " + id);

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID USER -> Usuario encontrado con éxito");

        logger.info("ACCION DELETEBYID USER -> Eliminando usuario");

        userRepository.deleteById(id);
    }

}
