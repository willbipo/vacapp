package com.vacapp.vacunas.internal.infrastructure.controllers;

import com.vacapp.vacunas.internal.domain.model.VacunaNoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/** Manejo centralizado de errores del módulo vacunas. */
@RestControllerAdvice
public class VacunasExceptionHandler {

    @ExceptionHandler(VacunaNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> manejarVacunaNoEncontrada(VacunaNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
