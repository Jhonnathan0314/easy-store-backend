package com.easy.store.backend.context.store_request.application.usecase;

import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidActionException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RejectStoreRequestUseCase {

    private final StoreRequestRepository storeRequestRepository;

    public StoreRequest reject(Long id, Long adminId) throws NoResultsException, InvalidActionException {

        log.info("ACCION REJECT STORE_REQUEST -> Iniciando proceso con id: {}", id);

        Optional<StoreRequest> optStoreRequest = storeRequestRepository.findById(id);
        if(optStoreRequest.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION REJECT STORE_REQUEST -> Solicitud encontrada con éxito");

        StoreRequest storeRequest = optStoreRequest.get();
        if(!storeRequest.isPending()) throw new InvalidActionException(ErrorMessages.STORE_REQUEST_NOT_PENDING);
        log.info("ACCION REJECT STORE_REQUEST -> Validé que la solicitud está pendiente");

        storeRequest.setStatus(StoreRequest.STATUS_REJECTED);
        storeRequest.setReviewedBy(adminId);

        log.info("ACCION REJECT STORE_REQUEST -> Actualizando solicitud a rejected");

        return storeRequestRepository.update(storeRequest);
    }

}
