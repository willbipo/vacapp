package com.vacapp.usuarios.internal.infrastructure.config;

import com.vacapp.usuarios.internal.application.usecases.RegistrarUsuarioUseCase;
import com.vacapp.usuarios.internal.application.usecases.RegistrarUsuarioUseCase.ComandoRegistroUsuario;
import com.vacapp.usuarios.internal.domain.model.Rol;
import com.vacapp.usuarios.internal.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Siembra el usuario administrador inicial si la base de datos está vacía.
 * Se ejecuta una sola vez al arrancar la aplicación.
 *
 * Credenciales por defecto:
 *   username : admin
 *   password : Admin123!
 *   tenantId : tenant-default
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "Admin123!";
    private static final String ADMIN_TENANT   = "tenant-default";

    private final UsuarioRepository     usuarioRepository;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.buscarPorUsername(ADMIN_USERNAME).isPresent()) {
            log.info("[DataSeeder] Usuario '{}' ya existe — no se crea de nuevo.", ADMIN_USERNAME);
            return;
        }

        ComandoRegistroUsuario comando = new ComandoRegistroUsuario(
                ADMIN_USERNAME,
                "admin@vacapp.com",
                ADMIN_PASSWORD,
                Rol.ADMIN,
                ADMIN_TENANT
        );

        registrarUsuarioUseCase.ejecutar(comando);

        log.info("========================================================");
        log.info("[DataSeeder] Usuario admin creado exitosamente.");
        log.info("  Username : {}", ADMIN_USERNAME);
        log.info("  Password : {}", ADMIN_PASSWORD);
        log.info("  TenantId : {}", ADMIN_TENANT);
        log.info("  Rol      : ADMIN");
        log.info("  CAMBIA LA CONTRASEÑA después del primer inicio de sesión.");
        log.info("========================================================");
    }
}
