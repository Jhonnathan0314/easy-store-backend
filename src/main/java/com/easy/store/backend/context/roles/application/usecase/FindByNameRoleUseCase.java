package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByNameRoleUseCase {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String name) throws NoResultsException {

        log.info("ACCION FINDBYNAME ROLE -> Iniciando búsqueda");

        Optional<Role> optionalRole = roleRepository.findByName(name);;
        if(optionalRole.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYNAME ROLE -> Encontré rol con éxito");

        return roleRepository.findByName(name);
    }

}
