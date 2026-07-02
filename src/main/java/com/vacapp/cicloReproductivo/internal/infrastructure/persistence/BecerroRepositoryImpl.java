package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

import com.vacapp.cicloReproductivo.internal.domain.model.Becerro;
import com.vacapp.cicloReproductivo.internal.domain.repository.BecerroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/** Implementación del puerto {@link BecerroRepository}. */
@Repository
@RequiredArgsConstructor
public class BecerroRepositoryImpl implements BecerroRepository {

    private final BecerroJdbcRepository jdbcRepository;
    private final BecerroMapper mapper;

    @Override
    @Transactional
    public Becerro guardar(Becerro becerro) {
        return mapper.aDominio(jdbcRepository.save(mapper.aEntidad(becerro)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Becerro> listarPorMadre(UUID madreId, String tenantId) {
        return jdbcRepository.findByMadreIdAndTenantId(madreId, tenantId).stream().map(mapper::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Becerro> listarPorTenant(String tenantId) {
        return jdbcRepository.findAllByTenantId(tenantId).stream().map(mapper::aDominio).toList();
    }
}
