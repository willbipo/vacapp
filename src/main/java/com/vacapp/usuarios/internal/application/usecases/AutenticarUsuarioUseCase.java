package com.vacapp.usuarios.internal.application.usecases;

import com.vacapp.usuarios.CredencialesInvalidasException;
import com.vacapp.usuarios.internal.domain.model.Usuario;
import com.vacapp.usuarios.DatosToken;
import com.vacapp.usuarios.GeneradorDeToken;
import com.vacapp.usuarios.internal.domain.repository.UsuarioRepository;
import com.vacapp.usuarios.internal.domain.repository.VerificadorDeContrasena;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Autenticar usuario.
 * Valida las credenciales y retorna un token JWT con la información del tenant.
 */
@Service
@RequiredArgsConstructor
public class AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final VerificadorDeContrasena verificadorDeContrasena;
    private final GeneradorDeToken generadorDeToken;

    /**
     * Resultado de la autenticación exitosa.
     *
     * @param token     JWT firmado.
     * @param username  Nombre de usuario.
     * @param role      Rol del usuario.
     * @param tenantId  Identificador del tenant.
     */
    public record ResultadoAutenticacion(String token, String username, String role, String tenantId) {}

    /**
     * Autentica al usuario y genera el token JWT.
     *
     * @param username  Nombre de usuario.
     * @param password  Contraseña en texto plano.
     * @return Resultado con token, username, role y tenantId.
     * @throws CredencialesInvalidasException si el usuario no existe o la contraseña no coincide.
     */
    public ResultadoAutenticacion ejecutar(String username, String password) {
        Usuario usuario = usuarioRepository
                .buscarPorUsername(username)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!verificadorDeContrasena.verificar(password, usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        DatosToken datosToken = new DatosToken(usuario.getUsername(), usuario.getRole().name(), usuario.getTenantId());
        String token = generadorDeToken.generar(datosToken);

        return new ResultadoAutenticacion(token, usuario.getUsername(), usuario.getRole().name(), usuario.getTenantId());
    }
}
