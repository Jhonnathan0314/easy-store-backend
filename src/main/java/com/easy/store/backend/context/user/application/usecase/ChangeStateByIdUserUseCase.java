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
public class ChangeStateByIdUserUseCase {

    private final Logger logger = Logger.getLogger(ChangeStateByIdUserUseCase.class.getName());

    private final UserRepository userRepository;

    public User changeStateById(Long id) throws NonExistenceException {

        logger.info("ACCION CHANGESTATEBYID USER -> Iniciando proceso con id: " + id);

        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CHANGESTATEBYID USER -> Usuario encontrado con éxito");

        User user = optUser.get();
        user.setState(user.getState().equals("active") ? "inactive" : "active");

        logger.info("ACCION CHANGESTATEBYID USER -> Actualizando usuario");
        return userRepository.update(user);
    }
}
