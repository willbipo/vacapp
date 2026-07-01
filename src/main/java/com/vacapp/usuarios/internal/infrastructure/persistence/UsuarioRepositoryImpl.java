package com.vacapp.usuarios.internal.infrastructure.persistence;

import com.vacapp.usuarios.internal.domain.model.Usuario;
import com.vacapp.usuarios.internal.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementación del puerto {@link UsuarioRepository} usando Spring Data JDBC.
 */
@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository dataJdbcRepository;
    private final UsuarioMapper mapper;

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        UsuarioEntidad entidad = mapper.aEntidad(usuario);
        UsuarioEntidad guardada = dataJdbcRepository.save(entidad);
        return mapper.aDominio(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return dataJdbcRepository.findByUsername(username).map(mapper::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorUsername(String username) {
        return dataJdbcRepository.existsByUsername(username);
    }
}
