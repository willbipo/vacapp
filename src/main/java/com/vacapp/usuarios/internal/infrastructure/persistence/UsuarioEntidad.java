package com.vacapp.usuarios.internal.infrastructure.persistence;

import com.vacapp.usuarios.internal.domain.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

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
@Table("usuarios")
public class UsuarioEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Rol role;
    private String tenantId;

    /** Siempre es nueva: el id se genera en la capa de aplicación. */
    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
