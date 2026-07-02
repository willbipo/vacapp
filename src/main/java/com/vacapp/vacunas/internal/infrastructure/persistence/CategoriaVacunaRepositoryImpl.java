package com.vacapp.vacunas.internal.infrastructure.persistence;

import com.vacapp.vacunas.internal.domain.model.CategoriaVacuna;
import com.vacapp.vacunas.internal.domain.repository.CategoriaVacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Implementación del puerto {@link CategoriaVacunaRepository}. */
@Repository
@RequiredArgsConstructor
public class CategoriaVacunaRepositoryImpl implements CategoriaVacunaRepository {

    private final CategoriaVacunaJpaRepository jpaRepository;

    @Override
    @Transactional
    public CategoriaVacuna guardar(CategoriaVacuna categoria) {
        CategoriaVacunaEntidad entidad = CategoriaVacunaEntidad.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .tenantId(categoria.getTenantId())
                .build();
        return toDominio(jpaRepository.save(entidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaVacuna> listarPorTenant(String tenantId) {
        return jpaRepository.findAllByTenantId(tenantId).stream()
                .map(this::toDominio)
                .toList();
    }

    private CategoriaVacuna toDominio(CategoriaVacunaEntidad e) {
        return CategoriaVacuna.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .tenantId(e.getTenantId())
                .build();
    }
}
