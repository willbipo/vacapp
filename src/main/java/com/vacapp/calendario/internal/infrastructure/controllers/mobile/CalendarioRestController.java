package com.vacapp.calendario.internal.infrastructure.controllers.mobile;

import com.vacapp.calendario.internal.application.usecases.EliminarEventoCalendarioUseCase;
import com.vacapp.calendario.internal.application.usecases.ListarEventosCalendarioUseCase;
import com.vacapp.calendario.internal.application.usecases.RegistrarEventoCalendarioUseCase;
import com.vacapp.calendario.internal.infrastructure.controllers.mobile.dtos.EventoCalendarioRequest;
import com.vacapp.calendario.internal.infrastructure.controllers.mobile.dtos.EventoCalendarioResponse;
import com.vacapp.core.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

/** Controlador REST para eventos personalizados del calendario. */
@RestController
@RequestMapping("/api/v1/calendario")
@RequiredArgsConstructor
public class CalendarioRestController {

    private final RegistrarEventoCalendarioUseCase registrarUseCase;
    private final ListarEventosCalendarioUseCase listarUseCase;
    private final EliminarEventoCalendarioUseCase eliminarUseCase;

    /**
     * Lista eventos del calendario para un mes dado.
     * GET /api/v1/calendario?anio=2025&mes=6
     */
    @GetMapping
    public ResponseEntity<List<EventoCalendarioResponse>> listar(
            @RequestParam int anio,
            @RequestParam int mes) {
        String tenantId = TenantContext.obtenerTenant();
        YearMonth ym = YearMonth.of(anio, mes);
        LocalDate inicio = ym.atDay(1);
        LocalDate fin = ym.atEndOfMonth();
        List<EventoCalendarioResponse> lista = listarUseCase.ejecutar(inicio, fin, tenantId)
                .stream().map(EventoCalendarioResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    /**
     * Crea un nuevo evento personalizado.
     * POST /api/v1/calendario
     */
    @PostMapping
    public ResponseEntity<EventoCalendarioResponse> crear(
            @Valid @RequestBody EventoCalendarioRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        var cmd = new RegistrarEventoCalendarioUseCase.Comando(
                req.titulo(), req.descripcion(), req.fecha(), req.tipo(), tenantId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EventoCalendarioResponse.desde(registrarUseCase.ejecutar(cmd)));
    }

    /**
     * Elimina un evento personalizado.
     * DELETE /api/v1/calendario/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        String tenantId = TenantContext.obtenerTenant();
        eliminarUseCase.ejecutar(id, tenantId);
        return ResponseEntity.noContent().build();
    }
}
