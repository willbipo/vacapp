package com.vacapp.ranchos;

import com.vacapp.ranchos.internal.application.usecases.*;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API pública del módulo Rancho.
 * Único punto de entrada para inter-módulos.
 */
@Service
@RequiredArgsConstructor
public class RanchoService {
    
    private final ListarRanchosUseCase listarRanchosUseCase;
    private final RegistrarSeccionUseCase registrarSeccionUseCase;
    private final ListarSeccionesUseCase listarSeccionesUseCase;
    private final ObtenerSeccionUseCase obtenerSeccionUseCase;
    private final ActualizarSeccionUseCase actualizarSeccionUseCase;
    private final EliminarSeccionUseCase eliminarSeccionUseCase;
    private final RegistrarPotreroUseCase registrarPotreroUseCase;
    private final ListarPotrerosUseCase listarPotrerosUseCase;
    private final ListarPotrerosPorSeccionUseCase listarPotrerosPorSeccionUseCase;
    private final ObtenerPotreroUseCase obtenerPotreroUseCase;
    private final ActualizarPotreroUseCase actualizarPotreroUseCase;
    private final EliminarPotreroUseCase eliminarPotreroUseCase;
    private final AsignarPersonalARanchoUseCase asignarPersonalARanchoUseCase;
    private final ListarPersonalPorRanchoUseCase listarPersonalPorRanchoUseCase;
    private final ListarRanchosPorEmpleadoUseCase listarRanchosPorEmpleadoUseCase;
    private final DesasignarPersonalDeRanchoUseCase desasignarPersonalDeRanchoUseCase;
    
    // ==================== RANCHOS ====================
    
    /**
     * Obtiene todos los ranchos del usuario autenticado.
     */
    public List<Rancho> obtenerRanchosPorUsuario(String userId, String tenantId) {
        return listarRanchosUseCase.ejecutar(userId, tenantId);
    }
    
    // ==================== SECCIONES ====================
    
    /**
     * Registra una nueva sección en un rancho.
     */
    public Seccion registrarSeccion(String ranchoId, String tenantId, String nombre) {
        return registrarSeccionUseCase.ejecutar(ranchoId, tenantId, nombre);
    }
    
    /**
     * Obtiene todas las secciones de un rancho.
     */
    public List<Seccion> obtenerSeccionesPorRancho(String ranchoId, String tenantId) {
        return listarSeccionesUseCase.ejecutar(ranchoId, tenantId);
    }
    
    /**
     * Obtiene una sección por ID.
     */
    public Seccion obtenerSeccionPorId(String id, String tenantId) {
        return obtenerSeccionUseCase.ejecutar(id, tenantId);
    }
    
    /**
     * Actualiza una sección.
     */
    public Seccion actualizarSeccion(String id, String tenantId, String nombre) {
        return actualizarSeccionUseCase.ejecutar(id, tenantId, nombre);
    }
    
    /**
     * Elimina una sección.
     */
    public void eliminarSeccion(String id, String tenantId) {
        eliminarSeccionUseCase.ejecutar(id, tenantId);
    }
    
    // ==================== POTREROS ====================
    
    /**
     * Registra un nuevo potrero en un rancho (sin sección).
     */
    public Potrero registrarPotrero(String ranchoId, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        return registrarPotreroUseCase.ejecutar(ranchoId, tenantId, nombre, hectareas, tipoPasto);
    }
    
    /**
     * Registra un nuevo potrero en una sección.
     */
    public Potrero registrarPotreroEnSeccion(String ranchoId, String seccionId, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        return registrarPotreroUseCase.ejecutar(ranchoId, seccionId, tenantId, nombre, hectareas, tipoPasto);
    }
    
    /**
     * Obtiene todos los potreros de un rancho.
     */
    public List<Potrero> obtenerPotrerosPorRancho(String ranchoId, String tenantId) {
        return listarPotrerosUseCase.ejecutar(ranchoId, tenantId);
    }
    
    /**
     * Obtiene todos los potreros de una sección.
     */
    public List<Potrero> obtenerPotrerosPorSeccion(String seccionId, String tenantId) {
        return listarPotrerosPorSeccionUseCase.ejecutar(seccionId, tenantId);
    }
    
    /**
     * Obtiene un potrero por ID.
     */
    public Potrero obtenerPotreroPorId(String id, String tenantId) {
        return obtenerPotreroUseCase.ejecutar(id, tenantId);
    }
    
    /**
     * Actualiza un potrero.
     */
    public Potrero actualizarPotrero(String id, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        return actualizarPotreroUseCase.ejecutar(id, tenantId, nombre, hectareas, tipoPasto);
    }
    
    /**
     * Elimina un potrero.
     */
    public void eliminarPotrero(String id, String tenantId) {
        eliminarPotreroUseCase.ejecutar(id, tenantId);
    }
    
    // ==================== PERSONAL RANCHO ====================
    
    /**
     * Asigna un empleado a un rancho.
     */
    public PersonalRancho asignarPersonalARancho(String empleadoId, String ranchoId, String tenantId) {
        return asignarPersonalARanchoUseCase.ejecutar(empleadoId, ranchoId, tenantId);
    }
    
    /**
     * Obtiene todo el personal asignado a un rancho.
     */
    public List<PersonalRancho> obtenerPersonalPorRancho(String ranchoId, String tenantId) {
        return listarPersonalPorRanchoUseCase.ejecutar(ranchoId, tenantId);
    }
    
    /**
     * Obtiene todos los ranchos asignados a un empleado.
     */
    public List<PersonalRancho> obtenerRanchosPorEmpleado(String empleadoId, String tenantId) {
        return listarRanchosPorEmpleadoUseCase.ejecutar(empleadoId, tenantId);
    }
    
    /**
     * Desasigna un empleado de un rancho.
     */
    public void desasignarPersonalDeRancho(String empleadoId, String ranchoId, String tenantId) {
        desasignarPersonalDeRanchoUseCase.ejecutar(empleadoId, ranchoId, tenantId);
    }
}
