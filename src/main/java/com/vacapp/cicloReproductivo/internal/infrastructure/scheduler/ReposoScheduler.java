package com.vacapp.cicloReproductivo.internal.infrastructure.scheduler;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;
import com.vacapp.cicloReproductivo.internal.domain.repository.CicloReproductivoRepository;
import com.vacapp.ganado.GanadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Tarea programada: todos los días a las 6 AM revisa vacas en reposo
 * cuyo período ya venció y las regresa a estado VIGENTE.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ReposoScheduler {

    private final CicloReproductivoRepository cicloRepository;
    private final GanadoService ganadoService;

    @Scheduled(cron = "0 0 6 * * *")
    public void liberarVacasDeReposo() {
        LocalDate hoy = LocalDate.now();
        List<CicloReproductivo> vencidos = cicloRepository.listarReposoVencido(hoy);

        if (vencidos.isEmpty()) return;

        log.info("Scheduler reposo: {} vaca(s) pasan a VIGENTE hoy {}", vencidos.size(), hoy);

        for (CicloReproductivo ciclo : vencidos) {
            try {
                ganadoService.cambiarEstatus(ciclo.getVacaId(), "VIGENTE", ciclo.getTenantId());
                log.info("Vaca {} cambió a VIGENTE (ciclo {})", ciclo.getVacaId(), ciclo.getId());
            } catch (Exception e) {
                log.error("Error al liberar vaca {} del reposo: {}", ciclo.getVacaId(), e.getMessage());
            }
        }
    }
}
