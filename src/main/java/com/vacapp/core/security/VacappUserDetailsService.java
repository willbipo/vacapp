package com.vacapp.core.security;

import com.vacapp.usuarios.UsuariosService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de {@link UserDetailsService} para Spring Security.
 * Carga el usuario a través de la API pública del módulo de usuarios.
 */
@Service
@RequiredArgsConstructor
public class VacappUserDetailsService implements UserDetailsService {

    private final UsuariosService usuariosService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosService
                .buscarPorUsername(username)
                .map(usuario -> User.builder()
                        .username(usuario.getUsername())
                        .password(usuario.getPassword())
                        .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
