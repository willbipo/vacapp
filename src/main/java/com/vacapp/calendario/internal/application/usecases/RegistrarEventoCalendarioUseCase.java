package com.vacapp.calendario.internal.application.usecases;

import com.vacapp.calendario.internal.domain.model.EventoCalendario;
import com.vacapp.calendario.internal.domain.repository.EventoCalendarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: registrar un evento personalizado en el calendario. */
@Service
@RequiredArgsConstructor
public class RegistrarEventoCalendarioUseCase {

    private final EventoCalendarioRepository repository;

    public record Comando(
            String titulo,
            String descripcion,
            LocalDate fecha,
            String tipo,
            String tenantId
    ) {}

    @Transactional
    public EventoCalendario ejecutar(Comando cmd) {
        EventoCalendario evento = EventoCalendario.builder()
                .id(UUID.randomUUID())
                .titulo(cmd.titulo())
                .descripcion(cmd.descripcion())
                .fecha(cmd.fecha())
                .tipo(cmd.tipo() != null ? cmd.tipo() : "OTRO")
                .tenantId(cmd.tenantId())
                .build();
        return repository.guardar(evento);
    }
}
