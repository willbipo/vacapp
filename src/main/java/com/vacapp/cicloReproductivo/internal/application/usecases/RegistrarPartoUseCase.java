package com.vacapp.cicloReproductivo.internal.application.usecases;

import com.vacapp.cicloReproductivo.internal.domain.model.Becerro;
import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;
import com.vacapp.cicloReproductivo.internal.domain.repository.BecerroRepository;
import com.vacapp.cicloReproductivo.internal.domain.repository.CicloReproductivoRepository;
import com.vacapp.ganado.GanadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: registrar el parto, el becerro y poner la vaca en reposo. */
@Service
@RequiredArgsConstructor
public class RegistrarPartoUseCase {

    private final CicloReproductivoRepository cicloRepository;
    private final BecerroRepository becerroRepository;
    private final GanadoService ganadoService;

    public record Comando(
            UUID cicloId,
            LocalDate fechaPartoReal,
            int diasReposo,
            // Datos del becerro
            String nombreBecerro,
            LocalDate fechaNacimientoBecerro,
            String sexoBecerro,
            String nombrePadre,
            String razaPadre,
            String notasBecerro,
            String tenantId
    ) {}

    public record Resultado(CicloReproductivo ciclo, Becerro becerro) {}

    @Transactional
    public Resultado ejecutar(Comando cmd) {
        CicloReproductivo ciclo = cicloRepository.buscarPorId(cmd.cicloId(), cmd.tenantId())
                .orElseThrow(() -> new IllegalArgumentException("Ciclo no encontrado: " + cmd.cicloId()));

        LocalDate fechaFinReposo = cmd.fechaPartoReal().plusDays(cmd.diasReposo());

        ciclo.setFechaPartoReal(cmd.fechaPartoReal());
        ciclo.setDiasReposo(cmd.diasReposo());
        ciclo.setFechaFinReposo(fechaFinReposo);
        ciclo.setEstatus("COMPLETADO");

        CicloReproductivo cicloGuardado = cicloRepository.guardar(ciclo);

        // Registrar becerro
        Becerro becerro = Becerro.builder()
                .id(UUID.randomUUID())
                .nombre(cmd.nombreBecerro())
                .fechaNacimiento(cmd.fechaNacimientoBecerro())
                .sexo(cmd.sexoBecerro())
                .nombrePadre(cmd.nombrePadre())
                .razaPadre(cmd.razaPadre())
                .notas(cmd.notasBecerro())
                .madreId(ciclo.getVacaId())
                .cicloId(ciclo.getId())
                .tenantId(cmd.tenantId())
                .build();

        Becerro becerroGuardado = becerroRepository.guardar(becerro);

        // Cambiar estatus de la vaca a REPOSO con fechas
        ganadoService.ponerEnReposo(ciclo.getVacaId(), cmd.fechaPartoReal(), fechaFinReposo, cmd.tenantId());

        // Registrar el becerro automáticamente en el inventario de ganado
        String notaAnimal = "Registrado automáticamente al parto. Madre ID: " + ciclo.getVacaId()
                + (cmd.nombrePadre() != null ? ". Padre: " + cmd.nombrePadre() : "")
                + (cmd.notasBecerro() != null ? ". " + cmd.notasBecerro() : "");
        ganadoService.registrarAnimalDesdeBecerro(
                cmd.nombreBecerro(),
                cmd.sexoBecerro(),
                cmd.fechaNacimientoBecerro(),
                cmd.razaPadre(),
                notaAnimal,
                cmd.tenantId()
        );

        return new Resultado(cicloGuardado, becerroGuardado);
    }
}
