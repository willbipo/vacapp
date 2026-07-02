package com.vacapp.insumos.internal.infrastructure.persistence;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.repository.CategoriaInsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Implementación del puerto {@link CategoriaInsumoRepository}. */
@Repository
@RequiredArgsConstructor
public class CategoriaInsumoRepositoryImpl implements CategoriaInsumoRepository {

    private final CategoriaInsumoJpaRepository jpaRepository;

    @Override
    @Transactional
    public CategoriaInsumo guardar(CategoriaInsumo categoria) {
        CategoriaInsumoEntidad entidad = CategoriaInsumoEntidad.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .tenantId(categoria.getTenantId())
                .build();
        return toDominio(jpaRepository.save(entidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaInsumo> listarPorTenant(String tenantId) {
        return jpaRepository.findAllByTenantId(tenantId).stream()
                .map(this::toDominio)
                .toList();
    }

    private CategoriaInsumo toDominio(CategoriaInsumoEntidad e) {
        return CategoriaInsumo.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .tenantId(e.getTenantId())
                .build();
    }
}
