package com.vacapp.ranchos.internal.infrastructure.controllers.mobile;

import com.vacapp.ranchos.internal.domain.model.RanchoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador centralizado de excepciones para Rancho REST.
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.vacapp.ranchos.internal.infrastructure.controllers.mobile")
public class RanchoExceptionHandler {
    
    @ExceptionHandler(RanchoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRanchoNoEncontrado(RanchoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException e) {
        String errores = e.getBindingResult().getFieldErrors()
            .stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        log.warn("Validación fallida: {}", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("Validación fallida: " + errores));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("Argumento inválido: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    public record ErrorResponse(String error) {}
}
