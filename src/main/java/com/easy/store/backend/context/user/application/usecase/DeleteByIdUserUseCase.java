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
public class DeleteByIdUserUseCase {

    private final UserRepository userRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID USER -> Iniciando proceso con id: {}", id);

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID USER -> Usuario encontrado con éxito");

        log.info("ACCION DELETEBYID USER -> Eliminando usuario");

        userRepository.deleteById(id);
    }

}
