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
public class FindByIdRoleUseCase {

    private final Logger logger = Logger.getLogger(FindByIdRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role findById(Long id) throws NoResultsException {

        logger.info("ACCION FINDBYID ROLE -> Iniciando búsqueda con id: " + id);

        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION FINDBYID ROLE -> Encontré rol con éxito");

        return optionalRole.get();
    }

}
