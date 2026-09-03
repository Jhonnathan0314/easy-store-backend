package com.easy.store.backend.utils.exceptions;

public class ForbiddenActionException extends Exception {
    public ForbiddenActionException(String message) {
        super(message);
    }
}
