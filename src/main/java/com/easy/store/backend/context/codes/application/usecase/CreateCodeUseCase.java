package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class CreateCodeUseCase {

    private final CodeRepository codeRepository;

    public Code create(Code code) throws InvalidBodyException {
        if(!code.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        code.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return codeRepository.create(code);
    }

}
