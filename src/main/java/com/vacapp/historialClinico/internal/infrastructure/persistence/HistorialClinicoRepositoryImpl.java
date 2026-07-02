package com.vacapp.historialClinico.internal.infrastructure.persistence;

import com.vacapp.historialClinico.internal.domain.model.HistorialClinico;
import com.vacapp.historialClinico.internal.domain.repository.HistorialClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/** Implementación del puerto de persistencia usando Spring Data JDBC. */
@Repository
@RequiredArgsConstructor
public class HistorialClinicoRepositoryImpl implements HistorialClinicoRepository {

    private final HistorialClinicoJdbcRepository jdbcRepository;
    private final HistorialClinicoMapper mapper;

    @Override
    public HistorialClinico guardar(HistorialClinico historial) {
        HistorialClinicoEntidad entidad = mapper.aEntidad(historial);
        return mapper.aDominio(jdbcRepository.save(entidad));
    }

    @Override
    public List<HistorialClinico> listarPorAnimal(UUID animalId, String tenantId) {
        return jdbcRepository.findByAnimalIdAndTenantId(animalId, tenantId)
                .stream().map(mapper::aDominio).toList();
    }

    @Override
    public List<HistorialClinico> listarPorTenant(String tenantId) {
        return jdbcRepository.findAllByTenantId(tenantId)
                .stream().map(mapper::aDominio).toList();
    }

    @Override
    public List<HistorialClinico> listarProximasDosis(String tenantId) {
        return jdbcRepository.findProximasDosis(tenantId)
                .stream().map(mapper::aDominio).toList();
    }
}
