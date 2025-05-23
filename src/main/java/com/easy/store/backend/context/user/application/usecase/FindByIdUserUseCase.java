package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdUserUseCase {

    private final UserRepository userRepository;

    public User findById(Long id) throws NoResultsException {

        log.info("ACCION FINDBYID USER -> Iniciando búsqueda con id: {}", id);

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID USER -> Encontré usuario con éxito");

        return optionalUser.get();
    }

}
