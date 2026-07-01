package com.vacapp.core.security;

import com.vacapp.usuarios.internal.domain.repository.GeneradorDeToken;
import com.vacapp.usuarios.internal.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Proveedor JWT: genera y valida tokens firmados con HMAC-SHA256.
 * Implementa el puerto {@link GeneradorDeToken} para la capa de aplicación.
 */
@Slf4j
@Component
public class JwtTokenProvider implements GeneradorDeToken {

    private final SecretKey claveSecreta;
    private final long expiracionMs;

    public JwtTokenProvider(
            @Value("${vacapp.jwt.secreto}") String secreto,
            @Value("${vacapp.jwt.expiracion-ms}") long expiracionMs) {
        this.claveSecreta = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = expiracionMs;
    }

    /**
     * Genera un token JWT con los claims: sub (username), role y tenantId.
     */
    @Override
    public String generar(Usuario usuario) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expiracionMs);

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("role", usuario.getRole().name())
                .claim("tenantId", usuario.getTenantId())
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(claveSecreta)
                .compact();
    }

    /**
     * Extrae el username (subject) del token.
     */
    public String extraerUsername(String token) {
        return parsearClaims(token).getSubject();
    }

    /**
     * Extrae el tenantId del token.
     */
    public String extraerTenantId(String token) {
        return parsearClaims(token).get("tenantId", String.class);
    }

    /**
     * Extrae el role del token.
     */
    public String extraerRole(String token) {
        return parsearClaims(token).get("role", String.class);
    }

    /**
     * Valida que el token esté bien formado y no haya expirado.
     */
    public boolean esValido(String token) {
        try {
            parsearClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }

    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(claveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
