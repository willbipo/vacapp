package com.vacapp.ganado.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.ganado.internal.application.usecases.ActualizarAnimalUseCase;
import com.vacapp.ganado.internal.application.usecases.ListarAnimalesUseCase;
import com.vacapp.ganado.internal.application.usecases.RegistrarAnimalUseCase;
import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalRequest;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalResponse;
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

/** Controlador REST para el inventario de ganado. */
@RestController
@RequestMapping("/api/v1/animales")
@RequiredArgsConstructor
@Tag(name = "Ganado", description = "Operaciones del inventario de animales")
@SecurityRequirement(name = "bearerAuth")
public class GanadoRestController {

    private final RegistrarAnimalUseCase registrarAnimalUseCase;
    private final ListarAnimalesUseCase listarAnimalesUseCase;
    private final ActualizarAnimalUseCase actualizarAnimalUseCase;

    @PostMapping
        @Operation(summary = "Registrar animal", description = "Registra un nuevo animal para el tenant autenticado")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Animal registrado", content = @Content(schema = @Schema(implementation = AnimalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
        })
    public ResponseEntity<AnimalResponse> registrar(@Valid @RequestBody AnimalRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Animal animal = registrarAnimalUseCase.ejecutar(new RegistrarAnimalUseCase.Comando(
                req.numeroIdentificador(), req.estatus(), req.sexo(), req.raza(),
                req.fechaNacimiento(), req.meses(), req.fechaAretado(), req.tipo(),
                req.areteAnterior(), req.folioReemo(), req.nota(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalResponse.desde(animal));
    }

    @GetMapping
        @Operation(summary = "Listar animales", description = "Obtiene todos los animales del tenant autenticado")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de animales", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AnimalResponse.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
        })
    public ResponseEntity<List<AnimalResponse>> listar() {
        String tenantId = TenantContext.obtenerTenant();
        List<AnimalResponse> lista = listarAnimalesUseCase.ejecutar(tenantId)
                .stream().map(AnimalResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar animal", description = "Actualiza un animal existente del tenant autenticado")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animal actualizado", content = @Content(schema = @Schema(implementation = AnimalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Animal no encontrado", content = @Content)
        })
    public ResponseEntity<AnimalResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AnimalRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Animal animal = actualizarAnimalUseCase.ejecutar(new ActualizarAnimalUseCase.Comando(
                id, req.numeroIdentificador(), req.estatus(), req.sexo(), req.raza(),
                req.fechaNacimiento(), req.meses(), req.fechaAretado(), req.tipo(),
                req.areteAnterior(), req.folioReemo(), req.nota(), tenantId
        ));
        return ResponseEntity.ok(AnimalResponse.desde(animal));
    }
}
