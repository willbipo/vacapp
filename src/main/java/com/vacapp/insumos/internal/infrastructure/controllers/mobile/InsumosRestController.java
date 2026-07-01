package com.vacapp.insumos.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.insumos.internal.application.usecases.ActualizarInsumoUseCase;
import com.vacapp.insumos.internal.application.usecases.ListarInsumosUseCase;
import com.vacapp.insumos.internal.application.usecases.RegistrarInsumoUseCase;
import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos.InsumoRequest;
import com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos.InsumoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/** Controlador REST para el inventario de insumos. */
@RestController
@RequestMapping("/api/v1/insumos")
@RequiredArgsConstructor
public class InsumosRestController {

    private final RegistrarInsumoUseCase registrarInsumoUseCase;
    private final ListarInsumosUseCase listarInsumosUseCase;
    private final ActualizarInsumoUseCase actualizarInsumoUseCase;

    @PostMapping
    public ResponseEntity<InsumoResponse> registrar(@Valid @RequestBody InsumoRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Insumo insumo = registrarInsumoUseCase.ejecutar(new RegistrarInsumoUseCase.Comando(
                req.nombre(), req.categoria(), req.unidadMedida(), req.cantidad(),
                req.cantidadMinima(), req.descripcion(), req.proveedor(),
                req.precioUnitario(), req.ubicacion(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(InsumoResponse.desde(insumo));
    }

    @GetMapping
    public ResponseEntity<List<InsumoResponse>> listar() {
        String tenantId = TenantContext.obtenerTenant();
        List<InsumoResponse> lista = listarInsumosUseCase.ejecutar(tenantId)
                .stream().map(InsumoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody InsumoRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Insumo insumo = actualizarInsumoUseCase.ejecutar(new ActualizarInsumoUseCase.Comando(
                id, req.nombre(), req.categoria(), req.unidadMedida(), req.cantidad(),
                req.cantidadMinima(), req.descripcion(), req.proveedor(),
                req.precioUnitario(), req.ubicacion(), tenantId
        ));
        return ResponseEntity.ok(InsumoResponse.desde(insumo));
    }
}
