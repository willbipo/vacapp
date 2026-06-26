package com.vacapp.usuarios.application.usecases;

import com.vacapp.usuarios.application.ports.UsuarioRepository;
import com.vacapp.usuarios.application.ports.VerificadorDeContrasena;
import com.vacapp.usuarios.domain.models.Usuario;
import com.vacapp.usuarios.presentation.dtos.RegistroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Registrar usuario.
 * Hashea la contraseña con BCrypt y persiste el nuevo usuario.
 */
@Service
@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final VerificadorDeContrasena verificadorDeContrasena;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request DTO con los datos del nuevo usuario.
     * @return El usuario persistido.
     * @throws IllegalArgumentException si el username ya está en uso.
     */
    public Usuario ejecutar(RegistroRequest request) {
        if (usuarioRepository.existePorUsername(request.username())) {
            throw new IllegalArgumentException("El nombre de usuario '" + request.username() + "' ya está en uso.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .username(request.username())
                .email(request.email())
                .password(verificadorDeContrasena.hashear(request.password()))
                .role(request.role())
                .tenantId(request.tenantId())
                .build();

        return usuarioRepository.guardar(nuevoUsuario);
    }
}
