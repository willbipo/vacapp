package com.vacapp.ganado.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.ganado.internal.application.usecases.ActualizarAnimalUseCase;
import com.vacapp.ganado.internal.application.usecases.CrearCategoriaGanadoUseCase;
import com.vacapp.ganado.internal.application.usecases.ListarAnimalesUseCase;
import com.vacapp.ganado.internal.application.usecases.ListarCategoriasGanadoUseCase;
import com.vacapp.ganado.internal.application.usecases.RegistrarAnimalUseCase;
import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalRequest;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalResponse;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.CategoriaGanadoRequest;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.CategoriaGanadoResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/** Controlador REST para el inventario de ganado. */
@RestController
@RequestMapping("/api/v1/animales")
@RequiredArgsConstructor
public class GanadoRestController {

    private final RegistrarAnimalUseCase registrarAnimalUseCase;
    private final ListarAnimalesUseCase listarAnimalesUseCase;
    private final ActualizarAnimalUseCase actualizarAnimalUseCase;
    private final CrearCategoriaGanadoUseCase crearCategoriaGanadoUseCase;
    private final ListarCategoriasGanadoUseCase listarCategoriasGanadoUseCase;

    @PostMapping
    public ResponseEntity<AnimalResponse> registrar(@Valid @RequestBody AnimalRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Animal animal = registrarAnimalUseCase.ejecutar(new RegistrarAnimalUseCase.Comando(
                req.numeroIdentificador(), req.estatus(), req.sexo(), req.raza(),
                req.fechaNacimiento(), req.meses(), req.fechaAretado(), req.tipo(),
                req.areteAnterior(), req.folioReemo(), req.nota(), req.categoria(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalResponse.desde(animal));
    }

    @GetMapping("/buscar")
    public ResponseEntity<AnimalResponse> buscarPorArete(@RequestParam String arete) {
        String tenantId = TenantContext.obtenerTenant();
        return listarAnimalesUseCase.ejecutar(tenantId).stream()
                .filter(a -> a.getNumeroIdentificador() != null
                        && a.getNumeroIdentificador().toLowerCase().contains(arete.toLowerCase()))
                .findFirst()
                .map(a -> ResponseEntity.ok(AnimalResponse.desde(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> listar() {
        String tenantId = TenantContext.obtenerTenant();
        List<AnimalResponse> lista = listarAnimalesUseCase.ejecutar(tenantId)
                .stream().map(AnimalResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AnimalRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        Animal animal = actualizarAnimalUseCase.ejecutar(new ActualizarAnimalUseCase.Comando(
                id, req.numeroIdentificador(), req.estatus(), req.sexo(), req.raza(),
                req.fechaNacimiento(), req.meses(), req.fechaAretado(), req.tipo(),
                req.areteAnterior(), req.folioReemo(), req.nota(), req.categoria(), tenantId
        ));
        return ResponseEntity.ok(AnimalResponse.desde(animal));
    }

    /* ── Categorías ──────────────────────────────────────────────────────── */

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaGanadoResponse> crearCategoria(
            @Valid @RequestBody CategoriaGanadoRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CategoriaGanadoResponse.desde(
                        crearCategoriaGanadoUseCase.ejecutar(
                                new CrearCategoriaGanadoUseCase.Comando(req.nombre(), tenantId))));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaGanadoResponse>> listarCategorias() {
        String tenantId = TenantContext.obtenerTenant();
        return ResponseEntity.ok(
                listarCategoriasGanadoUseCase.ejecutar(tenantId)
                        .stream().map(CategoriaGanadoResponse::desde).toList());
    }
}

