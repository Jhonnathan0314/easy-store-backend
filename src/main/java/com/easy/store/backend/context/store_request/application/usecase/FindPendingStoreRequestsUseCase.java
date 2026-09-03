package com.easy.store.backend.context.store_request.application.usecase;

import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindPendingStoreRequestsUseCase {

    private final StoreRequestRepository storeRequestRepository;

    public List<StoreRequest> findPending() throws NoResultsException {

        log.info("ACCION FINDPENDING STORE_REQUEST -> Iniciando búsqueda de solicitudes pendientes");

        List<StoreRequest> storeRequests = storeRequestRepository.findPendingRequests();
        if(storeRequests == null || storeRequests.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDPENDING STORE_REQUEST -> Encontré solicitudes pendientes con éxito");

        return storeRequests;
    }

}
