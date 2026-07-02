package com.vacapp.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuración global de OpenAPI para documentar la API móvil de Vacapp. */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI vacappOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vacapp API")
                        .version("v1")
                        .description("Documentación OpenAPI de los endpoints REST usados por la aplicación móvil de Vacapp.")
                        .contact(new Contact().name("Equipo Vacapp")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
