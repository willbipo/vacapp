package com.vacapp.usuarios;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Se lanza cuando las credenciales de inicio de sesión son inválidas. */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Credenciales inválidas. Verifica tu usuario y contraseña.");
    }

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
