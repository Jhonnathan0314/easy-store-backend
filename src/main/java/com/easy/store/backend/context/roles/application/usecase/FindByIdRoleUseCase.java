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
public class FindByIdRoleUseCase {

    private final RoleRepository roleRepository;

    public Role findById(Long id) throws NoResultsException {

        log.info("ACCION FINDBYID ROLE -> Iniciando búsqueda con id: {}", id);

        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID ROLE -> Encontré rol con éxito");

        return optionalRole.get();
    }

}
