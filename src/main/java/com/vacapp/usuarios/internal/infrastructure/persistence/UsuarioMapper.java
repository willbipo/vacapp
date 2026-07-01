package com.vacapp.usuarios.internal.infrastructure.persistence;

import com.vacapp.usuarios.internal.domain.model.Usuario;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre {@link UsuarioEntidad} (JPA) y {@link Usuario} (dominio).
 */
@Component
public class UsuarioMapper {

    /** Convierte una entidad JPA a un modelo de dominio. */
    public Usuario aDominio(UsuarioEntidad entidad) {
        return Usuario.builder()
                .id(entidad.getId())
                .username(entidad.getUsername())
                .email(entidad.getEmail())
                .password(entidad.getPassword())
                .role(entidad.getRole())
                .tenantId(entidad.getTenantId())
                .build();
    }

    /** Convierte un modelo de dominio a una entidad JPA. */
    public UsuarioEntidad aEntidad(Usuario usuario) {
        return UsuarioEntidad.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .role(usuario.getRole())
                .tenantId(usuario.getTenantId())
                .build();
    }
}
