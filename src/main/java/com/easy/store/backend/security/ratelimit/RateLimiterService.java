package com.easy.store.backend.security.ratelimit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Rate limiter simple en memoria, de ventana deslizante, por clave (IP + ruta). Pensado para
 * limitar intentos de login/registro y frenar fuerza bruta o abuso de registro masivo.
 *
 * No es distribuido: cada instancia del backend lleva su propio conteo. Es una limitacion
 * aceptada porque el despliegue actual es un unico contenedor (ver docker-compose.yml); si en el
 * futuro se escala a varias instancias, esto deberia moverse a un almacen compartido (ej. Redis).
 */
@Service
public class RateLimiterService {

    @Value("${easy.store.rate-limit.auth.max-attempts:10}")
    private int maxAttempts;

    @Value("${easy.store.rate-limit.auth.window-seconds:60}")
    private long windowSeconds;

    private final ConcurrentHashMap<String, Deque<Long>> attemptsByKey = new ConcurrentHashMap<>();

    /**
     * Registra un intento para la clave dada y devuelve true si esta dentro del limite permitido,
     * o false si ya se supero el maximo de intentos en la ventana de tiempo configurada.
     */
    public boolean tryAcquire(String key) {
        long now = System.currentTimeMillis();
        long windowStart = now - Duration.ofSeconds(windowSeconds).toMillis();

        Deque<Long> timestamps = attemptsByKey.computeIfAbsent(key, k -> new ConcurrentLinkedDeque<>());

        synchronized (timestamps) {
            while (!timestamps.isEmpty() && timestamps.peekFirst() < windowStart) {
                timestamps.pollFirst();
            }

            if (timestamps.size() >= maxAttempts) {
                return false;
            }

            timestamps.addLast(now);
            return true;
        }
    }

}
