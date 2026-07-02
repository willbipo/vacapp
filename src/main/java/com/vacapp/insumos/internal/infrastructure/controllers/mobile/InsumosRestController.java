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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST del inventario de insumos.
 * Implementa la interfaz generada desde openapi-insumos.yaml (Design-First).
 * Sin anotaciones Swagger propias — el contrato vive en el YAML.
 */
@RestController
@RequiredArgsConstructor
public class InsumosRestController implements InsumosApi {

    private final RegistrarInsumoUseCase registrarInsumoUseCase;
    private final ListarInsumosUseCase listarInsumosUseCase;
    private final ActualizarInsumoUseCase actualizarInsumoUseCase;

    @Override
    public ResponseEntity<InsumoResponse> registrarInsumo(@Valid @RequestBody InsumoRequest insumoRequest) {
        String tenantId = TenantContext.obtenerTenant();
        Insumo insumo = registrarInsumoUseCase.ejecutar(new RegistrarInsumoUseCase.Comando(
                insumoRequest.nombre(), insumoRequest.categoria(), insumoRequest.unidadMedida(),
                insumoRequest.cantidad(), insumoRequest.cantidadMinima(), insumoRequest.descripcion(),
                insumoRequest.proveedor(), insumoRequest.precioUnitario(), insumoRequest.ubicacion(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(InsumoResponse.desde(insumo));
    }

    @Override
    public ResponseEntity<List<InsumoResponse>> listarInsumos() {
        String tenantId = TenantContext.obtenerTenant();
        List<InsumoResponse> lista = listarInsumosUseCase.ejecutar(tenantId)
                .stream().map(InsumoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<InsumoResponse> actualizarInsumo(@PathVariable("id") UUID id,
                                                            @Valid @RequestBody InsumoRequest insumoRequest) {
        String tenantId = TenantContext.obtenerTenant();
        Insumo insumo = actualizarInsumoUseCase.ejecutar(new ActualizarInsumoUseCase.Comando(
                id, insumoRequest.nombre(), insumoRequest.categoria(), insumoRequest.unidadMedida(),
                insumoRequest.cantidad(), insumoRequest.cantidadMinima(), insumoRequest.descripcion(),
                insumoRequest.proveedor(), insumoRequest.precioUnitario(), insumoRequest.ubicacion(), tenantId
        ));
        return ResponseEntity.ok(InsumoResponse.desde(insumo));
    }
}
