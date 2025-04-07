package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindAllUserUseCase {

    private final UserRepository userRepository;

    public List<User> findAll() throws NoResultsException {

        log.info("ACCION FINDALL USER -> Iniciando búsqueda");

        List<User> users = userRepository.findAll();
        if(users == null || users.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL USER -> Encontré usuarios con éxito");

        return users;
    }

}
