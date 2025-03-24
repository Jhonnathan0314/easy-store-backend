package com.easy.store.backend.context.codes.application.usecase;

import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.codes.domain.port.CodeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteByUserIdCodeUseCase {

    private final CodeRepository codeRepository;
    private final FindByUserIdCodeUseCase findByUserIdCodeUseCase;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteByUserId(Long userId) throws NoResultsException, NonExistenceException {
        Code codeDb = findByUserIdCodeUseCase.findByUserId(userId);
        if(codeDb == null) throw new NoResultsException(errorMessages.NO_RESULTS);
        codeRepository.deleteByUserId(userId);
    }

}
