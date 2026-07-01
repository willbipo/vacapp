package com.vacapp.ganado.internal.infrastructure.controllers;

import com.vacapp.ganado.internal.domain.model.AnimalNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/** Manejo centralizado de errores del módulo ganado. */
@RestControllerAdvice
public class GanadoExceptionHandler {

    @ExceptionHandler(AnimalNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarAnimalNoEncontrado(AnimalNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
