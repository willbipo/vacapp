package com.vacapp.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import java.util.List;
import java.util.UUID;

/**
 * Configuración de Spring Data JDBC.
 * Registra repositorios y conversores UUID↔String para MySQL CHAR(36).
 */
@Configuration
@EnableJdbcRepositories(basePackages = {
    "com.vacapp.usuarios.internal.infrastructure.persistence",
    "com.vacapp.ganado.internal.infrastructure.persistence",
    "com.vacapp.insumos.internal.infrastructure.persistence",
    "com.vacapp.vacunas.internal.infrastructure.persistence",
    "com.vacapp.ventas.internal.infrastructure.persistence",
    "com.vacapp.historialClinico.internal.infrastructure.persistence",
    "com.vacapp.calendario.internal.infrastructure.persistence",
    "com.vacapp.cicloReproductivo.internal.infrastructure.persistence"
})
public class DataJdbcConfig extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
        return List.of(
            new StringToUuidConverter(),
            new UuidToStringConverter()
        );
    }

    /** Conversor de lectura: MySQL CHAR(36) → UUID en Java. */
    @ReadingConverter
    static class StringToUuidConverter implements Converter<String, UUID> {
        @Override
        public UUID convert(String source) {
            return UUID.fromString(source);
        }
    }

    /** Conversor de escritura: UUID en Java → CHAR(36) en MySQL. */
    @WritingConverter
    static class UuidToStringConverter implements Converter<UUID, String> {
        @Override
        public String convert(UUID source) {
            return source.toString();
        }
    }
}
