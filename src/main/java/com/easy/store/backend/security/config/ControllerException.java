package com.easy.store.backend.security.config;

import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import com.easy.store.backend.utils.messages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
            NoIdReceivedException.class
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
            FileException.class,
            InsufficientStockException.class
    })
    public ResponseEntity<ApiResponse<ErrorMessage>> handleConflictExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * NonExistenceException representa unicamente el caso de "el recurso solicitado no existe"
     * (por ejemplo, actualizar/eliminar/cambiar estado de un id que no esta en base de datos).
     * Antes se mapeaba junto a las excepciones de BAD_REQUEST (400), lo que era inconsistente con
     * el resto de la API: NoResultsException (bajo el mismo significado de "no encontrado" en
     * flujos de lectura) ya mapea a 404. Los otros dos significados que antes compartia esta
     * misma excepcion (sin stock suficiente, codigo de recuperacion invalido) se movieron a
     * InsufficientStockException (409) e InvalidBodyException (400) respectivamente, porque no
     * son casos de "recurso no encontrado".
     */
    @ExceptionHandler({
            NoResultsException.class,
            NonExistenceException.class
    })
    public ResponseEntity<ApiResponse<ErrorMessage>> handleNotFoundExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = ForbiddenActionException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleForbiddenExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(value = TooManyRequestsException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleTooManyRequestsExceptions(final Exception ex) {
        return generateApiResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
    }

    /**
     * AuthorizationService.login llama a authenticationManager.authenticate(...) directamente
     * dentro de un metodo de servicio normal (no dentro de un filtro de Spring Security), asi que
     * cuando las credenciales son invalidas la AuthenticationException (ej. BadCredentialsException)
     * se propaga como una excepcion Java comun hacia el controller, en vez de ser interceptada por
     * AuthenticationError (ese AuthenticationEntryPoint solo se dispara cuando Spring Security
     * rechaza la peticion en el filtro chain, antes de llegar al controller). Sin este handler,
     * un login con password incorrecta caia en el handler generico de Exception y devolvia 500 en
     * vez de 401.
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleAuthenticationExceptions(final AuthenticationException ex) {
        return generateApiResponse(HttpStatus.UNAUTHORIZED, ErrorMessages.INVALID_CREDENTIALS);
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
