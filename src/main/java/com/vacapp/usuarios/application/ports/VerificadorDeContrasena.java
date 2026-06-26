package com.vacapp.usuarios.application.ports;

/**
 * Puerto de salida para la verificación de contraseñas.
 * La implementación delega en BCrypt (infraestructura).
 */
public interface VerificadorDeContrasena {

    /** Retorna {@code true} si la contraseña en texto plano coincide con el hash. */
    boolean verificar(String contrasenaPlana, String contrasenaHasheada);

    /** Hashea una contraseña en texto plano. */
    String hashear(String contrasenaPlana);
}
