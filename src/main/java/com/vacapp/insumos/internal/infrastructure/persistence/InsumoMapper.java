package com.vacapp.insumos.internal.infrastructure.persistence;

import com.vacapp.insumos.internal.domain.model.Insumo;
import org.springframework.stereotype.Component;

/** Mapea entre {@link Insumo} (dominio) y {@link InsumoEntidad} (JPA). */
@Component
public class InsumoMapper {

    public InsumoEntidad aEntidad(Insumo insumo) {
        return InsumoEntidad.builder()
                .id(insumo.getId())
                .nombre(insumo.getNombre())
                .categoria(insumo.getCategoria())
                .unidadMedida(insumo.getUnidadMedida())
                .cantidad(insumo.getCantidad())
                .cantidadMinima(insumo.getCantidadMinima())
                .descripcion(insumo.getDescripcion())
                .proveedor(insumo.getProveedor())
                .precioUnitario(insumo.getPrecioUnitario())
                .ubicacion(insumo.getUbicacion())
                .tenantId(insumo.getTenantId())
                .build();
    }

    public Insumo aDominio(InsumoEntidad entidad) {
        return Insumo.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .categoria(entidad.getCategoria())
                .unidadMedida(entidad.getUnidadMedida())
                .cantidad(entidad.getCantidad())
                .cantidadMinima(entidad.getCantidadMinima())
                .descripcion(entidad.getDescripcion())
                .proveedor(entidad.getProveedor())
                .precioUnitario(entidad.getPrecioUnitario())
                .ubicacion(entidad.getUbicacion())
                .tenantId(entidad.getTenantId())
                .build();
    }
}
