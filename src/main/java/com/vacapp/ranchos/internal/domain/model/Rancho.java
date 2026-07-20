package com.vacapp.ranchos.internal.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio Rancho.
 * Representa una unidad de negocio (finca/granja) dentro de un tenant.
 * Un usuario FARMER puede administrar múltiples ranchos.
 */
public class Rancho {
    private final String id;
    private final String tenantId;
    private final String userId;  // Usuario FARMER propietario del rancho
    private String nombre;
    private String descripcion;
    private Double hectareas;
    private String ubicacion;
    private final LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;

    // RELACIONES FLEXIBLES: Un rancho puede organizarse por secciones o tener potreros directos
    private final List<Seccion> secciones;
    private final List<Potrero> potrerosDirectos;

    /**
     * Constructor original (adaptado).
     * Inicializa las colecciones de secciones y potreros directos como listas vacías.
     */
    public Rancho(
        String id,
        String tenantId,
        String userId,
        String nombre,
        String descripcion,
        Double hectareas,
        String ubicacion,
        LocalDateTime fechaRegistro,
        LocalDateTime fechaActualizacion
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.userId = userId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hectareas = hectareas;
        this.ubicacion = ubicacion;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
        this.secciones = new ArrayList<>();
        this.potrerosDirectos = new ArrayList<>();
    }

    /**
     * Constructor completo sobrecargado.
     * Útil para que los mapeadores de la capa de infraestructura (JPA/Repository) 
     * reconstruyan el Rancho con sus datos históricos de la base de datos.
     */
    public Rancho(
        String id,
        String tenantId,
        String userId,
        String nombre,
        String descripcion,
        Double hectareas,
        String ubicacion,
        LocalDateTime fechaRegistro,
        LocalDateTime fechaActualizacion,
        List<Seccion> secciones,
        List<Potrero> potrerosDirectos
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.userId = userId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hectareas = hectareas;
        this.ubicacion = ubicacion;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
        this.secciones = secciones != null ? new ArrayList<>(secciones) : new ArrayList<>();
        this.potrerosDirectos = potrerosDirectos != null ? new ArrayList<>(potrerosDirectos) : new ArrayList<>();
    }

    // ==========================================
    // REGLAS DE NEGOCIO / COMPORTAMIENTOS (DDD)
    // ==========================================

    /**
     * Agrega una sección al rancho asegurando que no se repitan nombres.
     */
    public void agregarSeccion(Seccion seccion) {
        if (seccion == null) {
            throw new IllegalArgumentException("La sección no puede ser nula.");
        }
        boolean nombreExiste = this.secciones.stream()
                .anyMatch(s -> s.getNombre().equalsIgnoreCase(seccion.getNombre()));
        if (nombreExiste) {
            throw new IllegalArgumentException("Ya existe una sección con el nombre: " + seccion.getNombre());
        }
        this.secciones.add(seccion);
    }

    /**
     * Registra un potrero de forma directa en el Rancho (SaaS adaptado a Ranchos pequeños).
     */
    public void agregarPotreroDirecto(Potrero potrero) {
        if (potrero == null) {
            throw new IllegalArgumentException("El potrero no puede ser nulo.");
        }
        if (potrero.getSeccionId().isPresent()) {
            throw new IllegalArgumentException("Este potrero tiene una sección asignada. Agrégalo usando la sección correspondiente.");
        }
        this.potrerosDirectos.add(potrero);
    }

    /**
     * Devuelve el total de hectáreas que se encuentran en uso real sumando 
     * todos los potreros directos y los potreros dentro de las secciones.
     */
    public Double calcularHectareasEnUso() {
        double hasDirectos = this.potrerosDirectos.stream()
                .mapToDouble(Potrero::getHectareas)
                .sum();
        double hasSecciones = this.secciones.stream()
                .mapToDouble(Seccion::calcularHectareasTotales)
                .sum();
        return hasDirectos + hasSecciones;
    }

    // ==========================================
    // GETTERS & SETTERS
    // ==========================================

    public String getId() { return id; }
    public String getTenantId() { return tenantId; }
    public String getUserId() { return userId; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Double getHectareas() { return hectareas; }
    public String getUbicacion() { return ubicacion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    /**
     * Retorna una copia de las secciones para proteger la inmutabilidad de la lista interna.
     */
    public List<Seccion> getSecciones() { 
        return new ArrayList<>(secciones); 
    }

    /**
     * Retorna una copia de los potreros directos para proteger la inmutabilidad de la lista interna.
     */
    public List<Potrero> getPotrerosDirectos() { 
        return new ArrayList<>(potrerosDirectos); 
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setHectareas(Double hectareas) { this.hectareas = hectareas; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}