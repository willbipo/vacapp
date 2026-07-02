package com.vacapp.vacunas.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.vacunas.internal.application.usecases.ActualizarVacunaUseCase;
import com.vacapp.vacunas.internal.application.usecases.CrearCategoriaVacunaUseCase;
import com.vacapp.vacunas.internal.application.usecases.ListarCategoriasVacunaUseCase;
import com.vacapp.vacunas.internal.application.usecases.ListarVacunasUseCase;
import com.vacapp.vacunas.internal.application.usecases.RegistrarVacunaUseCase;
import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos.CategoriaVacunaRequest;
import com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos.CategoriaVacunaResponse;
import com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos.VacunaRequest;
import com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos.VacunaResponse;
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

/** Controlador REST para el inventario de vacunas. */
@RestController
@RequestMapping("/api/v1/vacunas")
@RequiredArgsConstructor
public class VacunasRestController {

    private final RegistrarVacunaUseCase registrarVacunaUseCase;
    private final ListarVacunasUseCase listarVacunasUseCase;
    private final ActualizarVacunaUseCase actualizarVacunaUseCase;
    private final CrearCategoriaVacunaUseCase crearCategoriaVacunaUseCase;
    private final ListarCategoriasVacunaUseCase listarCategoriasVacunaUseCase;

    @PostMapping
    public ResponseEntity<VacunaResponse> registrar(@Valid @RequestBody VacunaRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Vacuna vacuna = registrarVacunaUseCase.ejecutar(new RegistrarVacunaUseCase.Comando(
                req.nombre(), req.tipo(), req.laboratorio(), req.descripcion(),
                req.dosis(), req.viaAdministracion(), req.lote(), req.fechaCaducidad(),
                req.stock(), req.unidadMedida(), req.temperaturaAlmacenamiento(), req.intervaloDias(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(VacunaResponse.desde(vacuna));
    }

    @GetMapping
    public ResponseEntity<List<VacunaResponse>> listar() {
        String tenantId = TenantContext.obtenerTenant();
        List<VacunaResponse> lista = listarVacunasUseCase.ejecutar(tenantId)
                .stream().map(VacunaResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacunaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody VacunaRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Vacuna vacuna = actualizarVacunaUseCase.ejecutar(new ActualizarVacunaUseCase.Comando(
                id, req.nombre(), req.tipo(), req.laboratorio(), req.descripcion(),
                req.dosis(), req.viaAdministracion(), req.lote(), req.fechaCaducidad(),
                req.stock(), req.unidadMedida(), req.temperaturaAlmacenamiento(), req.intervaloDias(), tenantId
        ));
        return ResponseEntity.ok(VacunaResponse.desde(vacuna));
    }

    /* ── Categorías ──────────────────────────────────────────────────────── */

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaVacunaResponse> crearCategoria(
            @Valid @RequestBody CategoriaVacunaRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CategoriaVacunaResponse.desde(
                        crearCategoriaVacunaUseCase.ejecutar(
                                new CrearCategoriaVacunaUseCase.Comando(req.nombre(), tenantId))));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaVacunaResponse>> listarCategorias() {
        String tenantId = TenantContext.obtenerTenant();
        return ResponseEntity.ok(
                listarCategoriasVacunaUseCase.ejecutar(tenantId)
                        .stream().map(CategoriaVacunaResponse::desde).toList());
    }
}

