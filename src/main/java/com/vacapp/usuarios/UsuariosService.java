package com.vacapp.usuarios;

import com.vacapp.usuarios.internal.domain.model.Usuario;
import com.vacapp.usuarios.internal.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * API pública del módulo de usuarios.
 * Es el único punto de entrada síncrono accesible por otros módulos del monolito.
 */
@Service
@RequiredArgsConstructor
public class UsuariosService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Busca un usuario por su nombre de usuario.
     * Expuesto para que otros módulos (ej. core/security) puedan cargarlo.
     */
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.buscarPorUsername(username);
    }
}
