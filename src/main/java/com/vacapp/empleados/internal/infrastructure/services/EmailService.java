package com.vacapp.empleados.internal.infrastructure.services;

import com.vacapp.empleados.internal.domain.model.Empleado;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio de envío de emails.
 * Actualmente registra el email en logs.
 * Puede ser extendido para usar SMTP, SendGrid, AWS SES, etc.
 */
@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    /**
     * Envía una invitación por correo al empleado.
     * Incluye un link para descargar la aplicación móvil.
     */
    public void enviarInvitacion(Empleado empleado) {
        String asunto = "Bienvenido a Vacapp - Descarga la aplicación";
        String cuerpo = construirCuerpoInvitacion(empleado);
        
        // TODO: Implementar envío real con SMTP, SendGrid, AWS SES, etc.
        // Por ahora solo log
        logger.info("=== INVITACIÓN DE EMAIL ===");
        logger.info("Para: {}", empleado.getEmail());
        logger.info("Asunto: {}", asunto);
        logger.info("Cuerpo:\n{}", cuerpo);
        logger.info("==========================");
    }

    private String construirCuerpoInvitacion(Empleado empleado) {
        // Mapear rol a descripción amigable
        String descripcionRol = mapearRolADescripcion(empleado.getRol().name());
        
        return String.format("""
            Hola %s,
            
            ¡Bienvenido a Vacapp! Tu cuenta ha sido creada con éxito.
            
            Tu rol: %s
            
            Para comenzar a usar la aplicación, descarga Vacapp desde:
            
            📱 iOS: https://apps.apple.com/app/vacapp
            📱 Android: https://play.google.com/store/apps/details?id=com.vacapp
            
            Una vez descargues la app, inicia sesión con:
            Email: %s
            
            Si tienes dudas, contacta al administrador.
            
            Saludos,
            Equipo Vacapp
            """,
            empleado.getNombre(),
            descripcionRol,
            empleado.getEmail()
        );
    }
    
    private String mapearRolADescripcion(String rol) {
        return switch (rol) {
            case "ADMIN" -> "Administrador Global";
            case "FARMER" -> "Gestor de Rancho";
            case "DOCTOR" -> "Veterinario";
            case "WORKER" -> "Trabajador";
            default -> rol;
        };
    }
}
