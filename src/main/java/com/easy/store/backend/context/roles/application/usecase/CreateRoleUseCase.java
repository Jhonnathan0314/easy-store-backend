package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;

    public Role create(Role role) throws DuplicatedException, InvalidBodyException {

        log.info("ACCION CREATE ROLE -> Iniciando proceso con body: {}", role.toString());

        if(!role.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE ROLE -> Validé cuerpo de la petición");

        if(roleRepository.findByName(role.getName()).isPresent()) throw new DuplicatedException(ErrorMessages.DUPLICATED);
        log.info("ACCION CREATE ROLE -> Validé rol no duplicado");

        log.info("ACCION CREATE ROLE -> Creando rol");

        return roleRepository.create(role);
    }

}
