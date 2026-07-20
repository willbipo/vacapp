package com.vacapp.insumos.internal.infrastructure.persistence;

import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.domain.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementación del puerto {@link InsumoRepository} usando Spring Data JPA. */
@Repository
@RequiredArgsConstructor
public class InsumoRepositoryImpl implements InsumoRepository {

    private final InsumoJpaRepository jpaRepository;
    private final InsumoMapper mapper;

    @Override
    @Transactional
    public Insumo guardar(Insumo insumo) {
        return mapper.aDominio(jpaRepository.save(mapper.aEntidad(insumo)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Insumo> buscarPorId(UUID id, String tenantId) {
        return jpaRepository.findByIdAndTenantId(id, tenantId).map(mapper::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insumo> listarPorTenant(String tenantId) {
        return jpaRepository.findAllByTenantId(tenantId).stream()
                .map(mapper::aDominio)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insumo> listarPorRancho(String ranchoId, String tenantId) {
        return jpaRepository.findByRanchoIdAndTenantId(ranchoId, tenantId).stream()
                .map(mapper::aDominio)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(UUID id, String tenantId) {
        jpaRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
