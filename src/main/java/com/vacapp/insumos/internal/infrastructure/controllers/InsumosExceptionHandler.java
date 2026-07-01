package com.vacapp.insumos.internal.infrastructure.controllers;

import com.vacapp.insumos.internal.domain.model.InsumoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/** Manejo centralizado de errores del módulo insumos. */
@RestControllerAdvice
public class InsumosExceptionHandler {

    @ExceptionHandler(InsumoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarInsumoNoEncontrado(InsumoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
