package com.vacapp.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.IOException;
import java.util.List;

/**
 * Configuración Design-First de OpenAPI para Vacapp.
 *
 * Estrategia: los 4 YAML de src/main/resources/openapi/ son la fuente de verdad.
 * En tiempo de ejecución se hace un merge de paths, schemas y tags de todos
 * los archivos para producir una única vista continua en Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    /** Definición raíz con metadatos globales y el esquema de seguridad. */
    @Bean
    public OpenAPI vacappOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vacapp API")
                        .version("v1")
                        .description("API REST consumida por la aplicación móvil Flutter de Vacapp.")
                        .contact(new Contact().name("Equipo Vacapp")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    /**
     * Customizer que fusiona los 4 YAML de openapi/ en la especificación única
     * que sirve Swagger UI. Se ejecuta después de que springdoc escanea
     * las anotaciones del classpath, sobreescribiendo con el contrato YAML.
     */
    @Bean
    public OpenApiCustomizer yamlMergeCustomizer() {
        return openApi -> {
            List<String> yamlFiles = List.of(
                    "classpath:openapi/openapi-auth.yaml",
                    "classpath:openapi/openapi-ganado.yaml",
                    "classpath:openapi/openapi-insumos.yaml",
                    "classpath:openapi/openapi-vacunas.yaml"
            );

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            for (String location : yamlFiles) {
                try {
                    Resource resource = resolver.getResource(location);
                    OpenAPI partial = new OpenAPIV3Parser()
                            .read(resource.getURL().toString());

                    if (partial == null) continue;

                    // Fusionar paths
                    if (partial.getPaths() != null) {
                        if (openApi.getPaths() == null) openApi.setPaths(new Paths());
                        partial.getPaths().forEach(openApi.getPaths()::addPathItem);
                    }

                    // Fusionar schemas (components)
                    if (partial.getComponents() != null && partial.getComponents().getSchemas() != null) {
                        if (openApi.getComponents() == null) openApi.setComponents(new Components());
                        if (openApi.getComponents().getSchemas() == null) {
                            openApi.getComponents().setSchemas(new java.util.LinkedHashMap<>());
                        }
                        partial.getComponents().getSchemas().forEach(
                                (name, schema) -> openApi.getComponents().getSchemas().putIfAbsent(name, schema));
                    }

                    // Fusionar tags
                    if (partial.getTags() != null) {
                        for (Tag tag : partial.getTags()) {
                            boolean exists = openApi.getTags() != null &&
                                    openApi.getTags().stream().anyMatch(t -> t.getName().equals(tag.getName()));
                            if (!exists) openApi.addTagsItem(tag);
                        }
                    }
                } catch (IOException e) {
                    // Archivo no encontrado — se omite sin detener el arranque
                    System.err.println("[OpenApiConfig] No se pudo leer " + location + ": " + e.getMessage());
                }
            }
        };
    }
}
