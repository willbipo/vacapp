package com.vacapp.usuarios.infrastructure.persistence;

import com.vacapp.usuarios.application.ports.UsuarioRepository;
import com.vacapp.usuarios.domain.models.Usuario;
import com.vacapp.usuarios.infrastructure.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementación del puerto {@link UsuarioRepository} usando Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper mapper;

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        UsuarioEntidad entidad = mapper.aEntidad(usuario);
        UsuarioEntidad guardada = jpaRepository.save(entidad);
        return mapper.aDominio(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return jpaRepository.findByUsername(username).map(mapper::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}
