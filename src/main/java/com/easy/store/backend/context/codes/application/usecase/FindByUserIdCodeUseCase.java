package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByUserIdCodeUseCase {

    private final CodeRepository codeRepository;

    public Code findByUserId(Long userId) throws NonExistenceException {
        Code codeDb = codeRepository.findByUserId(userId);
        if(codeDb == null) throw new NonExistenceException(ErrorMessages.NO_VALID_CODE);
        return codeDb;
    }

}
