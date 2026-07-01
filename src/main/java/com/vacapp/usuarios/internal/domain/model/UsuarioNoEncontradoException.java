package com.vacapp.usuarios.internal.domain.model;

/**
 * Se lanza cuando no se encuentra un usuario con el identificador o username especificado.
 */
public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(String username) {
        super("No se encontró el usuario: " + username);
    }
}
