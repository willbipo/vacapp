package com.vacapp.core.security;

import com.vacapp.usuarios.CredencialesInvalidasException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

/**
 * Manejo centralizado de errores de seguridad y excepciones del módulo de autenticación.
 */
@RestControllerAdvice
public class ManejadorDeExcepcionesSeguridad implements AuthenticationEntryPoint, AccessDeniedHandler {

    /** Retorna 401 cuando la petición no está autenticada. */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        escribirRespuestaError(response, HttpStatus.UNAUTHORIZED, "No autenticado. Token requerido.");
    }

    /** Retorna 403 cuando el usuario no tiene permisos suficientes. */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        escribirRespuestaError(response, HttpStatus.FORBIDDEN, "Acceso denegado. Permisos insuficientes.");
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, String>> manejarCredencialesInvalidas(CredencialesInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    private void escribirRespuestaError(HttpServletResponse response, HttpStatus status, String mensaje)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String json = "{\"error\":\"" + mensaje.replace("\"", "\\\"") + "\"}";
        response.getWriter().write(json);
    }
}
