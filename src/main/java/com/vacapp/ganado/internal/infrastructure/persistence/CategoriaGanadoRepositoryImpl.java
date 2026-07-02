package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.CategoriaGanado;
import com.vacapp.ganado.internal.domain.repository.CategoriaGanadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Implementación del puerto {@link CategoriaGanadoRepository}. */
@Repository
@RequiredArgsConstructor
public class CategoriaGanadoRepositoryImpl implements CategoriaGanadoRepository {

    private final CategoriaGanadoJpaRepository jpaRepository;

    @Override
    @Transactional
    public CategoriaGanado guardar(CategoriaGanado categoria) {
        CategoriaGanadoEntidad entidad = CategoriaGanadoEntidad.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .tenantId(categoria.getTenantId())
                .build();
        return toDominio(jpaRepository.save(entidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaGanado> listarPorTenant(String tenantId) {
        return jpaRepository.findAllByTenantId(tenantId).stream()
                .map(this::toDominio)
                .toList();
    }

    private CategoriaGanado toDominio(CategoriaGanadoEntidad e) {
        return CategoriaGanado.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .tenantId(e.getTenantId())
                .build();
    }
}
