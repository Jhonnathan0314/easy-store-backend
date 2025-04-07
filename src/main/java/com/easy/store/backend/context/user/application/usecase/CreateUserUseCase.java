package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User create(User user) throws DuplicatedException, InvalidBodyException {

        log.info("ACCION CREATE USER -> Iniciando proceso con body: {}", user.toString());

        if(user.getPassword() == null) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE USER -> Validé cuerpo de la petición");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(!user.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE USER -> Validé cuerpo de la petición");

        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicatedException(ErrorMessages.DUPLICATED);
        log.info("ACCION CREATE USER -> Validé usuario no duplicado");

        log.info("ACCION CREATE USER -> Creando usuario");

        return userRepository.create(user);
    }

}
