package com.vacapp.usuarios.presentation.controllers;

import com.vacapp.usuarios.application.usecases.AutenticarUsuarioUseCase;
import com.vacapp.usuarios.application.usecases.RegistrarUsuarioUseCase;
import com.vacapp.usuarios.domain.models.Usuario;
import com.vacapp.usuarios.presentation.dtos.LoginRequest;
import com.vacapp.usuarios.presentation.dtos.LoginResponse;
import com.vacapp.usuarios.presentation.dtos.RegistroRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * Rutas públicas: no requieren JWT.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    /**
     * Inicia sesión y retorna un token JWT.
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse respuesta = autenticarUsuarioUseCase.ejecutar(request);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * POST /api/v1/auth/registro
     */
    @PostMapping("/registro")
    public ResponseEntity<Void> registro(@Valid @RequestBody RegistroRequest request) {
        registrarUsuarioUseCase.ejecutar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
