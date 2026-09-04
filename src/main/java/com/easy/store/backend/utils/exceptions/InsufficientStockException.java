package com.easy.store.backend.utils.exceptions;

/**
 * Se lanza cuando la cantidad solicitada de un producto supera el stock
 * disponible. Antes se reutilizaba NonExistenceException para este caso, lo
 * que mapeaba a un HTTP 400 (BAD_REQUEST); al separar el mapeo de
 * NonExistenceException a 404 (recurso no encontrado), este caso pasa a
 * mapear a 409 (CONFLICT), que refleja mejor que el recurso si existe pero
 * la operacion no se puede completar por una regla de negocio (falta de
 * stock).
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}
