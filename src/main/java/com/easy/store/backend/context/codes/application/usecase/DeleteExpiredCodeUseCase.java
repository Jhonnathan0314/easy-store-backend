package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteExpiredCodeUseCase {

    private final CodeRepository codeRepository;

    public void deleteExpired(List<Code> codes) {
        log.info("ACCION DELETEEXPIRED CODE: Inicia proceso de validacion sobre {} códigos.", codes.size());
        for (Code code : codes) {
            if(Duration.between(code.getCreationDate().toInstant(), Instant.now()).toMinutes() >= 15) {
                log.info("ACCION DELETEEXPIRED CODE: Encuentro código {} vencido.", code.getCode());
                codeRepository.deleteByUserId(code.getUserId());
                log.info("ACCION DELETEEXPIRED CODE: Elimine código {} vencido.", code.getCode());
            }
        }
        log.info("ACCION DELETEEXPIRED CODE: Finalice eliminación de código");
    }

}
