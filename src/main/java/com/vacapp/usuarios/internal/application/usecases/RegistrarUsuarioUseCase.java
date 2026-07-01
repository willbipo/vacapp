package com.vacapp.usuarios.internal.application.usecases;

import com.vacapp.usuarios.internal.domain.model.Rol;
import com.vacapp.usuarios.internal.domain.model.Usuario;
import com.vacapp.usuarios.internal.domain.repository.UsuarioRepository;
import com.vacapp.usuarios.internal.domain.repository.VerificadorDeContrasena;
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
     * Comando con los datos necesarios para registrar un nuevo usuario.
     */
    public record ComandoRegistroUsuario(
            String username,
            String email,
            String password,
            Rol role,
            String tenantId
    ) {}

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param comando Datos del nuevo usuario.
     * @return El usuario persistido.
     * @throws IllegalArgumentException si el username ya está en uso.
     */
    public Usuario ejecutar(ComandoRegistroUsuario comando) {
        if (usuarioRepository.existePorUsername(comando.username())) {
            throw new IllegalArgumentException("El nombre de usuario '" + comando.username() + "' ya está en uso.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .username(comando.username())
                .email(comando.email())
                .password(verificadorDeContrasena.hashear(comando.password()))
                .role(comando.role())
                .tenantId(comando.tenantId())
                .build();

        return usuarioRepository.guardar(nuevoUsuario);
    }
}
