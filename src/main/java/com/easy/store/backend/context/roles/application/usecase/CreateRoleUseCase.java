package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreateRoleUseCase {

    private final Logger logger = Logger.getLogger(CreateRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role create(Role role) throws DuplicatedException, InvalidBodyException {

        logger.info("ACCION CREATE ROLE -> Iniciando proceso con body: " + role.toString());

        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION CREATE ROLE -> Validé cuerpo de la petición");

        if(roleRepository.findByName(role.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
        logger.info("ACCION CREATE ROLE -> Validé rol no duplicado");

        logger.info("ACCION CREATE ROLE -> Creando rol");

        return roleRepository.create(role);
    }

}
