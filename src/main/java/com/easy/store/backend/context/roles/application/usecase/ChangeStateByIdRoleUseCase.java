package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdRoleUseCase {

    private final Logger logger = Logger.getLogger(ChangeStateByIdRoleUseCase.class.getName());

    private final RoleRepository roleRepository;

    public Role changeStateById(Long id) throws NonExistenceException {

        logger.info("ACCION CHANGESTATEBYID ROLE -> Iniciando proceso con id: " + id);

        Optional<Role> optRole = roleRepository.findById(id);
        if(optRole.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION CHANGESTATEBYID ROLE -> Rol encontrado con éxito");

        Role role = optRole.get();
        role.setState(role.getState().equals("active") ? "inactive" : "active");

        logger.info("ACCION CHANGESTATEBYID ROLE -> Actualizando estado");
        return roleRepository.update(role);
    }

}
