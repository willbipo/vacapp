package com.vacapp.insumos.internal.application.usecases;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.domain.model.InsumoNoEncontradoException;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import com.vacapp.insumos.internal.domain.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/** Caso de uso: actualizar los datos de un insumo existente. */
@Service
@RequiredArgsConstructor
public class ActualizarInsumoUseCase {

    private final InsumoRepository insumoRepository;

    public record Comando(
        UUID id,
        String nombre,
        CategoriaInsumo categoria,
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
        Insumo insumo = insumoRepository.buscarPorId(cmd.id(), cmd.tenantId())
                .orElseThrow(() -> new InsumoNoEncontradoException(cmd.id().toString()));

        insumo.setNombre(cmd.nombre());
        insumo.setCategoria(cmd.categoria());
        insumo.setUnidadMedida(cmd.unidadMedida());
        insumo.setCantidad(cmd.cantidad());
        insumo.setCantidadMinima(cmd.cantidadMinima());
        insumo.setDescripcion(cmd.descripcion());
        insumo.setProveedor(cmd.proveedor());
        insumo.setPrecioUnitario(cmd.precioUnitario());
        insumo.setUbicacion(cmd.ubicacion());

        return insumoRepository.guardar(insumo);
    }
}
