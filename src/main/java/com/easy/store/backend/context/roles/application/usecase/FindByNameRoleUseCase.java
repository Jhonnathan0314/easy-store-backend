package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FindByNameRoleUseCase {

    private final Logger logger = Logger.getLogger(FindByNameRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Optional<Role> findByName(String name) throws NoResultsException {

        logger.info("ACCION FINDBYNAME ROLE -> Iniciando búsqueda");

        Optional<Role> optionalRole = roleRepository.findByName(name);;
        if(optionalRole.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYNAME ROLE -> Encontré rol con éxito");

        return roleRepository.findByName(name);
    }

}
