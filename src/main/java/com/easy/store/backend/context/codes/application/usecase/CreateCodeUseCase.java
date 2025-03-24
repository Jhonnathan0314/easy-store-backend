package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCodeUseCase {

    private final CodeRepository codeRepository;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public Code create(Code code) throws InvalidBodyException {
        if(!code.isValid(code)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        return codeRepository.create(code);
    }

}
