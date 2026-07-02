package com.vacapp.insumos.internal.application.usecases;

import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import com.vacapp.insumos.internal.domain.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/** Caso de uso: registrar un nuevo insumo. */
@Service
@RequiredArgsConstructor
public class RegistrarInsumoUseCase {

    private final InsumoRepository insumoRepository;

    public record Comando(
        String nombre,
        String categoria,
        UnidadMedida unidadMedida,
        Double cantidad,
        Double cantidadMinima,
        String descripcion,
        String proveedor,
        BigDecimal precioUnitario,
        String ubicacion,
        String tenantId
    ) {}

    @Transactional
    public Insumo ejecutar(Comando cmd) {
        Insumo insumo = Insumo.builder()
                .id(UUID.randomUUID())
                .nombre(cmd.nombre())
                .categoria(cmd.categoria())
                .unidadMedida(cmd.unidadMedida())
                .cantidad(cmd.cantidad())
                .cantidadMinima(cmd.cantidadMinima())
                .descripcion(cmd.descripcion())
                .proveedor(cmd.proveedor())
                .precioUnitario(cmd.precioUnitario())
                .ubicacion(cmd.ubicacion())
                .tenantId(cmd.tenantId())
                .build();
        return insumoRepository.guardar(insumo);
    }
}
