package com.vacapp.usuarios.internal.infrastructure.controllers.mobile;

import com.vacapp.usuarios.internal.application.usecases.AutenticarUsuarioUseCase;
import com.vacapp.usuarios.internal.application.usecases.AutenticarUsuarioUseCase.ResultadoAutenticacion;
import com.vacapp.usuarios.internal.application.usecases.RegistrarUsuarioUseCase;
import com.vacapp.usuarios.internal.application.usecases.RegistrarUsuarioUseCase.ComandoRegistroUsuario;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.LoginRequest;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.LoginResponse;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.RegistroRequest;
import com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos.UsuarioActualResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controlador REST para autenticación y registro de usuarios.
 * Rutas públicas: no requieren JWT.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Endpoints de login, logout y registro de usuarios")
public class AuthRestController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    /**
     * Inicia sesión y retorna un token JWT.
     * POST /api/v1/auth/login
     */
    @PostMapping("/login")
        @Operation(summary = "Iniciar sesión", description = "Autentica usuario y retorna JWT")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
        })
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
    @Operation(summary = "Cerrar sesión", description = "Elimina la cookie JWT de sesión")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sesión cerrada"),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
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
    @Operation(summary = "Obtener usuario actual", description = "Retorna el usuario autenticado actual")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario autenticado", content = @Content(schema = @Schema(implementation = UsuarioActualResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
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
        @Operation(summary = "Registrar usuario", description = "Registra un usuario nuevo")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
        })
    public ResponseEntity<Void> registro(@Valid @RequestBody RegistroRequest request) {
        log.info("Registrando usuario");
        ComandoRegistroUsuario comando = new ComandoRegistroUsuario(
                request.username(), request.email(), request.password(), request.role(), request.tenantId());
        registrarUsuarioUseCase.ejecutar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
