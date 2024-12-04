package com.easy.store.backend.utils.http;

import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ErrorMessage;
import org.springframework.http.HttpStatus;

public class HttpUtils {
    public HttpStatus determineHttpStatus(Exception e) {
        if (e instanceof DuplicatedException) return HttpStatus.CONFLICT;
        if (e instanceof InvalidBodyException || e instanceof NoIdReceivedException) return HttpStatus.BAD_REQUEST;
        if (e instanceof NoChangesException) return HttpStatus.NOT_ACCEPTABLE;
        if (e instanceof NonExistenceException | e instanceof NoResultsException) return HttpStatus.NOT_FOUND;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ErrorMessage determineErrorMessage(Exception e) {
        return ErrorMessage.builder()
                .code(determineHttpErrorCode(e))
                .title(determineHttpErrorTitle(e))
                .detail(e.getMessage())
                .build();
    }

    private int determineHttpErrorCode(Exception e) {
        if (e instanceof DuplicatedException) return HttpStatus.CONFLICT.value();
        if (e instanceof InvalidBodyException || e instanceof NoIdReceivedException) return HttpStatus.BAD_REQUEST.value();
        if (e instanceof NoChangesException) return HttpStatus.NOT_ACCEPTABLE.value();
        if (e instanceof NonExistenceException | e instanceof NoResultsException) return HttpStatus.NOT_FOUND.value();
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    private String determineHttpErrorTitle(Exception e) {
        if (e instanceof DuplicatedException) return HttpStatus.CONFLICT.name();
        if (e instanceof InvalidBodyException || e instanceof NoIdReceivedException) return HttpStatus.BAD_REQUEST.name();
        if (e instanceof NoChangesException) return HttpStatus.NOT_ACCEPTABLE.name();
        if (e instanceof NonExistenceException | e instanceof NoResultsException) return HttpStatus.NOT_FOUND.name();
        return HttpStatus.INTERNAL_SERVER_ERROR.name();
    }
}
