package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;
import com.vacapp.cicloReproductivo.internal.domain.repository.CicloReproductivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementación del puerto {@link CicloReproductivoRepository}. */
@Repository
@RequiredArgsConstructor
public class CicloReproductivoRepositoryImpl implements CicloReproductivoRepository {

    private final CicloReproductivoJdbcRepository jdbcRepository;
    private final CicloReproductivoMapper mapper;

    @Override
    @Transactional
    public CicloReproductivo guardar(CicloReproductivo ciclo) {
        // Si ya existe (tiene fechaPartoReal), usar UPDATE directo
        if (ciclo.getFechaPartoReal() != null) {
            jdbcRepository.updateParto(
                    ciclo.getId(),
                    ciclo.getEstatus(),
                    ciclo.getFechaPartoReal(),
                    ciclo.getDiasReposo(),
                    ciclo.getFechaFinReposo(),
                    ciclo.getTenantId()
            );
            return ciclo;
        }
        return mapper.aDominio(jdbcRepository.save(mapper.aEntidad(ciclo)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CicloReproductivo> buscarPorId(UUID id, String tenantId) {
        return jdbcRepository.findByIdAndTenantId(id, tenantId).map(mapper::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CicloReproductivo> listarPorTenant(String tenantId) {
        return jdbcRepository.findAllByTenantId(tenantId).stream().map(mapper::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CicloReproductivo> listarPorVaca(UUID vacaId, String tenantId) {
        return jdbcRepository.findByVacaIdAndTenantId(vacaId, tenantId).stream().map(mapper::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CicloReproductivo> listarActivos(String tenantId) {
        return jdbcRepository.findActivos(tenantId).stream().map(mapper::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CicloReproductivo> listarReposoVencido(LocalDate hoy) {
        return jdbcRepository.findReposoVencido(hoy).stream().map(mapper::aDominio).toList();
    }
}
