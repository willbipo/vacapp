package com.vacapp.usuarios.internal.domain.model;

/**
 * Se lanza cuando las credenciales de inicio de sesión son inválidas.
 */
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Credenciales inválidas. Verifica tu usuario y contraseña.");
    }

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
