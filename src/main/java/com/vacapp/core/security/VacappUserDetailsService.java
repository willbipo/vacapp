package com.vacapp.core.security;

import com.vacapp.usuarios.infrastructure.persistence.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de {@link UserDetailsService} para Spring Security.
 * Carga el usuario desde la base de datos por su username.
 */
@Service
@RequiredArgsConstructor
public class VacappUserDetailsService implements UserDetailsService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioJpaRepository
                .findByUsername(username)
                .map(entidad -> User.builder()
                        .username(entidad.getUsername())
                        .password(entidad.getPassword())
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + entidad.getRole().name())))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
