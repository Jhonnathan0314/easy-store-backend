package com.easy.store.backend.security.config;

import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import com.easy.store.backend.utils.messages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler({
            DuplicatedException.class,
            InvalidBodyException.class,
            NoChangesException.class,
            NoIdReceivedException.class,
            NonExistenceException.class
    })
    public ResponseEntity<ApiResponse<ErrorMessage>> handleBadRequestExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            InvalidActionException.class,
            FileException.class
    })
    public ResponseEntity<ApiResponse<ErrorMessage>> handleConflictExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value = NoResultsException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleNotFoundExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleUnexpectedException(final Exception ex) {
        return generateApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.GENERIC_ERROR);
    }

    private ResponseEntity<ApiResponse<ErrorMessage>> generateApiResponse(HttpStatus status, String message) {
        ErrorMessage error = ErrorMessage.builder()
                .code(status.value())
                .title(status.name())
                .detail(message)
                .build();
        ApiResponse<ErrorMessage> response = new ApiResponse<>();
        response.setError(error);
        return ResponseEntity.status(status).body(response);
    }

}
