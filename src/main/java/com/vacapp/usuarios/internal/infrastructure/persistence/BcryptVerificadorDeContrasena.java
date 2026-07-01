package com.vacapp.usuarios.internal.infrastructure.persistence;

import com.vacapp.usuarios.internal.domain.repository.VerificadorDeContrasena;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementación del puerto {@link VerificadorDeContrasena} usando BCrypt de Spring Security.
 */
@Component
@RequiredArgsConstructor
public class BcryptVerificadorDeContrasena implements VerificadorDeContrasena {

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean verificar(String contrasenaPlana, String contrasenaHasheada) {
        return passwordEncoder.matches(contrasenaPlana, contrasenaHasheada);
    }

    @Override
    public String hashear(String contrasenaPlana) {
        return passwordEncoder.encode(contrasenaPlana);
    }
}
