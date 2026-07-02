package com.vacapp.insumos.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.insumos.internal.application.usecases.ActualizarInsumoUseCase;
import com.vacapp.insumos.internal.application.usecases.ListarInsumosUseCase;
import com.vacapp.insumos.internal.application.usecases.RegistrarInsumoUseCase;
import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos.InsumoRequest;
import com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos.InsumoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Insumos", description = "Operaciones del inventario de insumos")
@SecurityRequirement(name = "bearerAuth")
public class InsumosRestController {

    private final RegistrarInsumoUseCase registrarInsumoUseCase;
    private final ListarInsumosUseCase listarInsumosUseCase;
    private final ActualizarInsumoUseCase actualizarInsumoUseCase;

    @PostMapping
        @Operation(summary = "Registrar insumo", description = "Registra un nuevo insumo para el tenant autenticado")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Insumo registrado", content = @Content(schema = @Schema(implementation = InsumoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
        })
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
        @Operation(summary = "Listar insumos", description = "Obtiene todos los insumos del tenant autenticado")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de insumos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InsumoResponse.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
        })
    public ResponseEntity<List<InsumoResponse>> listar() {
        String tenantId = TenantContext.obtenerTenant();
        List<InsumoResponse> lista = listarInsumosUseCase.ejecutar(tenantId)
                .stream().map(InsumoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar insumo", description = "Actualiza un insumo existente del tenant autenticado")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Insumo actualizado", content = @Content(schema = @Schema(implementation = InsumoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Insumo no encontrado", content = @Content)
        })
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
