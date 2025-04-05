package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final Logger logger = Logger.getLogger(CreateUserUseCase.class.getName());

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User create(User user) throws DuplicatedException, InvalidBodyException {

        logger.info("ACCION CREATE USER -> Iniciando proceso con body: " + user.toString());

        if(user.getPassword() == null) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        logger.info("ACCION CREATE USER -> Validé cuerpo de la petición");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(!user.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        logger.info("ACCION CREATE USER -> Validé cuerpo de la petición");

        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicatedException(ErrorMessages.DUPLICATED);
        logger.info("ACCION CREATE USER -> Validé usuario no duplicado");

        logger.info("ACCION CREATE USER -> Creando usuario");

        return userRepository.create(user);
    }

}
