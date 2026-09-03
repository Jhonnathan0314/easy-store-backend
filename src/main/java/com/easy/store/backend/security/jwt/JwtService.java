package com.easy.store.backend.security.jwt;

import com.easy.store.backend.context.user.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // HS256 requiere una clave de al menos 256 bits (32 bytes) una vez
    // decodificada de Base64. Con una clave mas corta, JJWT lanza una
    // excepcion de todos modos, pero prefermos fallar al arrancar con un
    // mensaje claro en vez de que el primer login/registro reciba un 500.
    private static final int MIN_KEY_BYTES = 32;

    @Value("${easy.store.jwt.secret}")
    private String secretKeyProperty;

    private Key signingKey;

    /**
     * Antes esta clave se leia con {@code System.getenv} en cada operacion de
     * firma/validacion, sin ninguna validacion: si la variable de entorno no
     * estaba definida (o era invalida), el fallo ocurria en tiempo de request
     * (500 en el primer login) en vez de al arrancar la aplicacion. Ahora se
     * lee una sola vez via {@code @Value} (que a su vez resuelve
     * SECRET_JWT_KEY desde el entorno, igual que el resto de configuracion
     * del proyecto) y se valida/decodifica al arranque.
     */
    @PostConstruct
    void init() {
        if (!StringUtils.hasText(secretKeyProperty)) {
            throw new IllegalStateException(
                    "SECRET_JWT_KEY no esta configurado. Defina esta variable de entorno con una " +
                    "clave Base64 de al menos 256 bits (32 bytes) para HS256, por ejemplo con: " +
                    "openssl rand -base64 32"
            );
        }

        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secretKeyProperty);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "SECRET_JWT_KEY no es un valor Base64 valido. Genere uno nuevo con: openssl rand -base64 32", e
            );
        }

        if (keyBytes.length < MIN_KEY_BYTES) {
            throw new IllegalStateException(
                    "SECRET_JWT_KEY es demasiado corta (" + keyBytes.length + " bytes); " +
                    "se requieren al menos " + MIN_KEY_BYTES + " bytes para HS256. " +
                    "Genere una nueva con: openssl rand -base64 32"
            );
        }

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user, Map<String, String> extraClaims) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        return signingKey;
    }

    public boolean isTokenValid(String token, User userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
