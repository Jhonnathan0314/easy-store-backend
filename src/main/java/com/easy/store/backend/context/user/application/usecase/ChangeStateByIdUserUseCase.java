package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeStateByIdUserUseCase {

    private final UserRepository userRepository;

    public User changeStateById(Long id) throws NonExistenceException {

        log.info("ACCION CHANGESTATEBYID USER -> Iniciando proceso con id: {}", id);

        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION CHANGESTATEBYID USER -> Usuario encontrado con éxito");

        User user = optUser.get();
        user.setState(user.getState().equals("active") ? "inactive" : "active");

        log.info("ACCION CHANGESTATEBYID USER -> Actualizando usuario");
        return userRepository.update(user);
    }
}
