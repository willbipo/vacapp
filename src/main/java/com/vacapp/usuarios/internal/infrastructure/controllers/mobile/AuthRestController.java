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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Controlador REST de autenticación y registro de usuarios.
 * Implementa la interfaz generada desde openapi-auth.yaml (Design-First).
 * Sin anotaciones Swagger propias — el contrato vive en el YAML.
 * Rutas públicas: no requieren JWT.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    /** Obtiene el HttpServletResponse del hilo actual de la petición. */
    private HttpServletResponse httpResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        ResultadoAutenticacion resultado = autenticarUsuarioUseCase.ejecutar(
                loginRequest.username(), loginRequest.password());

        // Emitir cookie HttpOnly para que el navegador la envíe en cada petición web
        HttpServletResponse response = httpResponse();
        Cookie cookie = new Cookie("vacapp_jwt", resultado.token());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 24 horas
        response.addCookie(cookie);

        LoginResponse respuesta = new LoginResponse(
                resultado.token(), resultado.username(), resultado.role(), resultado.tenantId());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        HttpServletResponse response = httpResponse();
        Cookie cookie = new Cookie("vacapp_jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioActualResponse> obtenerUsuarioActual() {
        // El principal se obtiene del SecurityContext, ya autenticado por el filtro JWT
        org.springframework.security.core.Authentication auth =
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new UsuarioActualResponse(auth.getName()));
    }

    @PostMapping("/registro")
    public ResponseEntity<Void> registro(@Valid @RequestBody RegistroRequest registroRequest) {
        registrarUsuarioUseCase.ejecutar(new ComandoRegistroUsuario(
                registroRequest.username(),
                registroRequest.email(),
                registroRequest.password(),
                registroRequest.role(),
                registroRequest.tenantId()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
