package com.vacapp.ventas.internal.infrastructure.persistence;

import com.vacapp.ventas.internal.domain.model.VentaGanado;
import com.vacapp.ventas.internal.domain.repository.VentaGanadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementación del puerto {@link VentaGanadoRepository} usando Spring Data JDBC. */
@Repository
@RequiredArgsConstructor
public class VentaGanadoRepositoryImpl implements VentaGanadoRepository {

    private final VentaGanadoJdbcRepository jdbcRepository;
    private final VentaGanadoMapper mapper;

    @Override
    public VentaGanado guardar(VentaGanado ventaGanado) {
        VentaGanadoEntidad entidad = mapper.aEntidad(ventaGanado);
        VentaGanadoEntidad guardada = jdbcRepository.save(entidad);
        return mapper.aDominio(guardada);
    }

    @Override
    public Optional<VentaGanado> buscarPorId(UUID id, String tenantId) {
        return jdbcRepository.findByIdAndTenantId(id, tenantId)
                .map(mapper::aDominio);
    }

    @Override
    public List<VentaGanado> listarPorTenant(String tenantId) {
        return jdbcRepository.findAllByTenantId(tenantId)
                .stream()
                .map(mapper::aDominio)
                .toList();
    }
}
