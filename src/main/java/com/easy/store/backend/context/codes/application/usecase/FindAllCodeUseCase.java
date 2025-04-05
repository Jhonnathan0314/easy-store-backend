package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllCodeUseCase {

    private final CodeRepository codeRepository;

    public List<Code> findAll() throws NoResultsException {
        List<Code> codesDb = codeRepository.findAll();
        if(codesDb.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        return codesDb;
    }

}
