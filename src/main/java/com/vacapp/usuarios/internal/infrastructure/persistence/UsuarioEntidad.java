package com.vacapp.usuarios.internal.infrastructure.persistence;

import com.vacapp.usuarios.internal.domain.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Clase de mapeo para la tabla {@code usuarios} en MySQL.
 * No es una entidad JPA — se mapea manualmente desde ResultSet con JDBC.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntidad {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private Rol role;
    private String tenantId;
}
