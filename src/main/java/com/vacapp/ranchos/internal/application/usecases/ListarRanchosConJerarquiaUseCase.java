package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import com.vacapp.ranchos.internal.infrastructure.persistence.RanchoAsignacionJpaRepository;
import com.vacapp.ranchos.internal.infrastructure.persistence.RanchoAsignacionEntity;
import com.vacapp.core.UserContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Caso de uso: Listar ranchos del usuario con jerarquía completa (secciones y potreros).
 * ADMIN ve todos los ranchos del tenant.
 * Otros roles ven ranchos que crearon + ranchos asignados.
 */
@Service
@RequiredArgsConstructor
public class ListarRanchosConJerarquiaUseCase {
    private static final Logger log = LoggerFactory.getLogger(ListarRanchosConJerarquiaUseCase.class);
    
    private final RanchoRepository ranchoRepository;
    private final SeccionRepository seccionRepository;
    private final PotreroRepository potreroRepository;
    private final RanchoAsignacionJpaRepository ranchoAsignacionJpaRepository;

    public List<Rancho> ejecutar(String userId, String tenantId) {
        log.info("[RANCHOS] ListarRanchosConJerarquiaUseCase - userId: {}, tenantId: {}", userId, tenantId);
        
        // Obtener el rol del usuario
        String userRole = UserContext.obtenerRol();
        boolean esAdmin = "ADMIN".equals(userRole);
        log.info("[RANCHOS] Rol del usuario: {}, esAdmin: {}", userRole, esAdmin);
        
        List<Rancho> ranchosBasicos;
        
        if (esAdmin) {
            // ADMIN ve todos los ranchos del tenant
            log.info("[RANCHOS] Usuario es ADMIN, obteniendo todos los ranchos del tenant");
            ranchosBasicos = ranchoRepository.obtenerTodosPorTenant(tenantId);
        } else {
            // Otros roles ven ranchos que crearon + ranchos asignados
            log.info("[RANCHOS] Usuario no es ADMIN, obteniendo ranchos creados + asignados");
            
            // Ranchos creados por el usuario
            List<Rancho> ranchosCreados = ranchoRepository.obtenerPorUsuario(userId, tenantId);
            log.info("[RANCHOS] Ranchos creados por el usuario: {}", ranchosCreados.size());
            
            // Ranchos asignados al usuario
            List<RanchoAsignacionEntity> asignaciones = ranchoAsignacionJpaRepository.findByUsuarioIdAndTenantId(userId, tenantId);
            log.info("[RANCHOS] Asignaciones encontradas: {}", asignaciones.size());
            
            List<Rancho> ranchosAsignados = new ArrayList<>();
            for (RanchoAsignacionEntity asignacion : asignaciones) {
                ranchoRepository.obtenerPorId(asignacion.getRanchoId(), tenantId)
                    .ifPresent(ranchosAsignados::add);
            }
            log.info("[RANCHOS] Ranchos asignados al usuario: {}", ranchosAsignados.size());
            
            // Combinar sin duplicados
            ranchosBasicos = new ArrayList<>();
            ranchosBasicos.addAll(ranchosCreados);
            ranchosBasicos.addAll(ranchosAsignados);
            ranchosBasicos = ranchosBasicos.stream()
                .distinct()
                .collect(Collectors.toList());
        }
        
        log.info("[RANCHOS] Ranchos básicos obtenidos: {}", ranchosBasicos.size());
        
        List<Rancho> ranchosConJerarquia = new ArrayList<>();
        
        // Para cada rancho, cargar sus secciones y potreros
        for (Rancho rancho : ranchosBasicos) {
            log.info("[RANCHOS] Procesando rancho: {} - {}", rancho.getId(), rancho.getNombre());
            
            // Cargar secciones del rancho
            List<Seccion> secciones = seccionRepository.obtenerPorRancho(rancho.getId(), tenantId);
            log.info("[RANCHOS] Secciones del rancho {}: {}", rancho.getId(), secciones.size());
            
            // Para cada sección, cargar sus potreros
            List<Seccion> seccionesConPotreros = new ArrayList<>();
            for (Seccion seccion : secciones) {
                List<Potrero> potrerosDeSeccion = potreroRepository.obtenerPorSeccion(seccion.getId(), tenantId);
                Seccion seccionConPotreros = new Seccion(
                    seccion.getId(),
                    seccion.getRanchoId(),
                    seccion.getNombre(),
                    potrerosDeSeccion
                );
                seccionesConPotreros.add(seccionConPotreros);
            }
            
            // Cargar potreros directos (sin sección)
            List<Potrero> potrerosDirectos = potreroRepository.obtenerPorRancho(rancho.getId(), tenantId)
                .stream()
                .filter(p -> p.getSeccionId().isEmpty())
                .toList();
            log.info("[RANCHOS] Potreros directos del rancho {}: {}", rancho.getId(), potrerosDirectos.size());
            
            // Reconstruir el rancho con jerarquía completa
            Rancho ranchoConJerarquia = new Rancho(
                rancho.getId(),
                rancho.getTenantId(),
                rancho.getUserId(),
                rancho.getNombre(),
                rancho.getDescripcion(),
                rancho.getHectareas(),
                rancho.getUbicacion(),
                rancho.getFechaRegistro(),
                rancho.getFechaActualizacion(),
                seccionesConPotreros,
                potrerosDirectos
            );
            
            ranchosConJerarquia.add(ranchoConJerarquia);
        }
        
        log.info("[RANCHOS] Ranchos con jerarquía retornados: {}", ranchosConJerarquia.size());
        return ranchosConJerarquia;
    }
}
