package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteExpiredCodeUseCase {

    private final Logger logger = Logger.getLogger(DeleteExpiredCodeUseCase.class.getName());

    private final CodeRepository codeRepository;

    public void deleteExpired(List<Code> codes) {
        logger.info("ACCION DELETEEXPIRED CODE: Inicia proceso de validacion sobre " + codes.size() + " códigos.");
        for (Code code : codes) {
            if(Duration.between(code.getCreationDate().toInstant(), Instant.now()).toMinutes() >= 15) {
                logger.info("ACCION DELETEEXPIRED CODE: Encuentro código " + code.getCode() + " vencido.");
                codeRepository.deleteByUserId(code.getUserId());
                logger.info("ACCION DELETEEXPIRED CODE: Elimine código " + code.getCode() + " vencido.");
            }
        }
        logger.info("ACCION DELETEEXPIRED CODE: Finalice eliminación de código");
    }

}
