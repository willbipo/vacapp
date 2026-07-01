package com.vacapp.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/** Configuración explícita para Spring Data JDBC.
 * 
 * Esta clase asegura que Spring Data JDBC se inicialice correctamente
 * y que todos los repositorios sean escaneados y registrados como beans.
 */
@Configuration
@EnableJdbcRepositories(basePackages = {
	"com.vacapp.usuarios.internal.infrastructure.persistence",
	"com.vacapp.ganado.internal.infrastructure.persistence",
	"com.vacapp.insumos.internal.infrastructure.persistence",
	"com.vacapp.vacunas.internal.infrastructure.persistence"
})
public class DataJdbcConfig {
}
