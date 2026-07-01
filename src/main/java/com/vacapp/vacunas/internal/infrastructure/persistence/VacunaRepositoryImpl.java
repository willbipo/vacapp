package com.vacapp.vacunas.internal.infrastructure.persistence;

import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.domain.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementación del puerto {@link VacunaRepository} usando Spring Data JPA. */
@Repository
@RequiredArgsConstructor
public class VacunaRepositoryImpl implements VacunaRepository {

    private final VacunaJpaRepository jpaRepository;
    private final VacunaMapper mapper;

    @Override
    @Transactional
    public Vacuna guardar(Vacuna vacuna) {
        return mapper.aDominio(jpaRepository.save(mapper.aEntidad(vacuna)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vacuna> buscarPorId(UUID id, String tenantId) {
        return jpaRepository.findByIdAndTenantId(id, tenantId).map(mapper::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorTenant(String tenantId) {
        return jpaRepository.findAllByTenantId(tenantId).stream()
                .map(mapper::aDominio)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(UUID id, String tenantId) {
        jpaRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
