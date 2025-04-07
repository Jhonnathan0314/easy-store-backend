package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindAllRoleUseCase {

    private final RoleRepository roleRepository;

    public List<Role> findAll() throws NoResultsException {

        log.info("ACCION FINDALL ROLE -> Iniciando búsqueda");

        List<Role> roles = roleRepository.findAll();
        if(roles == null || roles.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL ROLE -> Encontré roles con éxito");

        return roles;
    }

}
