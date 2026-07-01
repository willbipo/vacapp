package com.vacapp.usuarios.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidad de negocio pura que representa a un usuario de Vacapp.
 * No contiene anotaciones JPA ni dependencias de Spring.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /** Identificador único del usuario. */
    private UUID id;

    /** Nombre de usuario para iniciar sesión. */
    private String username;

    /** Correo electrónico del usuario. */
    private String email;

    /** Contraseña hasheada con BCrypt. */
    private String password;

    /** Rol del usuario dentro de la plataforma. */
    private Rol role;

    /** Identificador del tenant (rancho) al que pertenece este usuario. */
    private String tenantId;
}
