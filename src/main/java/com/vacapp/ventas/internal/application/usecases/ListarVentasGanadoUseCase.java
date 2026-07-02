package com.vacapp.ventas.internal.application.usecases;

import com.vacapp.ventas.internal.domain.model.VentaGanado;
import com.vacapp.ventas.internal.domain.repository.VentaGanadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar todas las ventas de ganado de un tenant. */
@Service
@RequiredArgsConstructor
public class ListarVentasGanadoUseCase {

    private final VentaGanadoRepository ventaGanadoRepository;

    @Transactional(readOnly = true)
    public List<VentaGanado> ejecutar(String tenantId) {
        return ventaGanadoRepository.listarPorTenant(tenantId);
    }
}
