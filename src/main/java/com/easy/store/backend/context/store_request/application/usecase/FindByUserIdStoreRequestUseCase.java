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
public class FindByUserIdStoreRequestUseCase {

    private final StoreRequestRepository storeRequestRepository;

    public List<StoreRequest> findByUserId(Long userId) throws NoResultsException {

        log.info("ACCION FINDBYUSERID STORE_REQUEST -> Iniciando búsqueda con id: {}", userId);

        List<StoreRequest> storeRequests = storeRequestRepository.findByUserId(userId);
        if(storeRequests == null || storeRequests.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYUSERID STORE_REQUEST -> Encontré solicitudes con éxito");

        return storeRequests;
    }

}
