package com.easy.store.backend.context.store_request.application.usecase;

import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidActionException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateStoreRequestUseCase {

    private static final String CLIENT_ROLE = "client";

    private final StoreRequestRepository storeRequestRepository;
    private final UserRepository userRepository;

    public StoreRequest create(StoreRequest storeRequest) throws
            InvalidBodyException, NoResultsException, InvalidActionException {

        log.info("ACCION CREATE STORE_REQUEST -> Iniciando proceso con body: {}", storeRequest.toString());

        if(!storeRequest.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION CREATE STORE_REQUEST -> Validé cuerpo de la petición");

        Optional<User> optUser = userRepository.findById(storeRequest.getUser().getId());
        if(optUser.isEmpty()) throw new NoResultsException(ErrorMessages.NO_USER_RESULTS);
        log.info("ACCION CREATE STORE_REQUEST -> Usuario encontrado con éxito");

        User user = optUser.get();
        if(user.getRole() == null || !CLIENT_ROLE.equalsIgnoreCase(user.getRole().getName())) {
            throw new InvalidActionException(ErrorMessages.STORE_REQUEST_NOT_CLIENT);
        }
        log.info("ACCION CREATE STORE_REQUEST -> Validé que el usuario tiene rol client");

        Optional<StoreRequest> optPending = storeRequestRepository.findLatestPendingByUserId(user.getId());
        if(optPending.isPresent()) throw new InvalidActionException(ErrorMessages.STORE_REQUEST_ALREADY_PENDING);
        log.info("ACCION CREATE STORE_REQUEST -> Validé que no hay solicitudes pendientes");

        storeRequest.setUser(user);
        storeRequest.setStatus(StoreRequest.STATUS_PENDING);

        log.info("ACCION CREATE STORE_REQUEST -> Creando solicitud de tienda");

        return storeRequestRepository.create(storeRequest);
    }

}
