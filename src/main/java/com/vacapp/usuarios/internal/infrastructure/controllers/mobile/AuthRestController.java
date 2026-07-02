package com.vacapp.usuarios.internal.infrastructure.controllers.mobile;

import com.vacapp.usuarios.internal.application.usecases.AutenticarUsuarioUseCase;
import com.vacapp.usuarios.internal.application.usecases.AutenticarUsuarioUseCase.ResultadoAutenticacion;
import com.vacapp.usuarios.internal.application.usecases.RegistrarUsuarioUseCase;
import com.vacapp.usuarios.internal.application.usecases.RegistrarUsuarioUseCase.ComandoRegistroUsuario;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.LoginRequest;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.LoginResponse;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.RegistroRequest;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.UsuarioActualResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * Rutas públicas: no requieren JWT.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    /**
     * Inicia sesión y retorna un token JWT.
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletResponse response) {
        ResultadoAutenticacion resultado = autenticarUsuarioUseCase.ejecutar(request.username(), request.password());

        // Emitir cookie HttpOnly para que el navegador la envíe en cada petición web
        Cookie cookie = new Cookie("vacapp_jwt", resultado.token());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 24 horas
        response.addCookie(cookie);

        LoginResponse respuesta = new LoginResponse(resultado.token(), resultado.username(), resultado.role(), resultado.tenantId());
        return ResponseEntity.ok(respuesta);
    }

    /** Cierra sesión borrando la cookie JWT. POST /api/v1/auth/logout */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("vacapp_jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    /**
     * Retorna el usuario actualmente autenticado.
     * GET /api/v1/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<UsuarioActualResponse> obtenerUsuarioActual(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new UsuarioActualResponse(principal.getName()));
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * POST /api/v1/auth/registro
     */
    @PostMapping("/registro")
    public ResponseEntity<Void> registro(@Valid @RequestBody RegistroRequest request) {
        log.info("Registrando usuario");
        ComandoRegistroUsuario comando = new ComandoRegistroUsuario(
                request.username(), request.email(), request.password(), request.role(), request.tenantId());
        registrarUsuarioUseCase.ejecutar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** Credenciales incorrectas → 401 con mensaje JSON. */
    @ExceptionHandler(com.vacapp.usuarios.CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, String>> handleCredencialesInvalidas(com.vacapp.usuarios.CredencialesInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", ex.getMessage()));
    }

    /** Username duplicado en registro → 409 con mensaje JSON. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("mensaje", ex.getMessage()));
    }
}
