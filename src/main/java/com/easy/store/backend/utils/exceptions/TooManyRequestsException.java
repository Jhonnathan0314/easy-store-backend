package com.easy.store.backend.utils.exceptions;

/**
 * Se lanza cuando se supera el limite de intentos permitidos en una ventana de tiempo (ver
 * RateLimiterService). Mapeada a 429 (TOO_MANY_REQUESTS) en ControllerException.
 */
public class TooManyRequestsException extends Exception {
    public TooManyRequestsException(String message) {
        super(message);
    }
}
