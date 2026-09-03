package com.easy.store.backend.security.config;

import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import com.easy.store.backend.utils.messages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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

    /**
     * Se lanza cuando falla una anotacion de Bean Validation (@Valid) en el body de un
     * @RequestBody. Antes de esto, cada DTO solo se validaba manualmente dentro de cada caso de
     * uso (isValid()), asi que un body con campos faltantes o vacios llegaba hasta el caso de uso
     * antes de rechazarse, y el mensaje de error era siempre el mismo INVALID_BODY generico sin
     * indicar que campo fallo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(" "));
        if (detail.isBlank()) detail = ErrorMessages.INVALID_BODY;
        return generateApiResponse(HttpStatus.BAD_REQUEST, detail);
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

    @ExceptionHandler(value = ForbiddenActionException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleForbiddenExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleUnexpectedException(final Exception ex) {
        return generateApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.GENERIC_ERROR);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage() + ".";
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
