package com.vacapp.usuarios.application.usecases;

import com.vacapp.usuarios.application.ports.GeneradorDeToken;
import com.vacapp.usuarios.application.ports.UsuarioRepository;
import com.vacapp.usuarios.application.ports.VerificadorDeContrasena;
import com.vacapp.usuarios.domain.exceptions.CredencialesInvalidasException;
import com.vacapp.usuarios.domain.models.Usuario;
import com.vacapp.usuarios.presentation.dtos.LoginRequest;
import com.vacapp.usuarios.presentation.dtos.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Autenticar usuario.
 * Valida las credenciales y retorna un token JWT con la información del tenant.
 */
@Service
@RequiredArgsConstructor
public class AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final VerificadorDeContrasena verificadorDeContrasena;
    private final GeneradorDeToken generadorDeToken;

    /**
     * Autentica al usuario y genera el token JWT.
     *
     * @param request DTO con username y password.
     * @return DTO con token, username, role y tenantId.
     * @throws CredencialesInvalidasException si el usuario no existe o la contraseña no coincide.
     */
    public LoginResponse ejecutar(LoginRequest request) {
        Usuario usuario = usuarioRepository
                .buscarPorUsername(request.username())
                .orElseThrow(CredencialesInvalidasException::new);

        if (!verificadorDeContrasena.verificar(request.password(), usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        String token = generadorDeToken.generar(usuario);

        return new LoginResponse(token, usuario.getUsername(), usuario.getRole().name(), usuario.getTenantId());
    }
}
