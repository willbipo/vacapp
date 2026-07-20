package com.vacapp.ranchos.internal.domain.model;

import java.util.Optional;

public class Potrero {
    private final String id;
    private final String ranchoId;
    private final String seccionId;
    private String nombre;
    private Double hectareas;
    private String tipoPasto;

    public Potrero(String id, String ranchoId, String nombre, Double hectareas, String tipoPasto) {
        this.id = id;
        this.ranchoId = ranchoId;
        this.seccionId = null; // Sin sección
        this.nombre = nombre;
        this.hectareas = hectareas;
        this.tipoPasto = tipoPasto;
    }

    public Potrero(String id, String ranchoId, String seccionId, String nombre, Double hectareas, String tipoPasto) {
        this.id = id;
        this.ranchoId = ranchoId;
        this.seccionId = seccionId;
        this.nombre = nombre;
        this.hectareas = hectareas;
        this.tipoPasto = tipoPasto;
    }

    public String getId() { return id; }
    public String getRanchoId() { return ranchoId; }
    
    public Optional<String> getSeccionId() { 
        return Optional.ofNullable(seccionId); 
    }
    
    public String getNombre() { return nombre; }
    public Double getHectareas() { return hectareas; }
    public String getTipoPasto() { return tipoPasto; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setHectareas(Double hectareas) { this.hectareas = hectareas; }
    public void setTipoPasto(String tipoPasto) { this.tipoPasto = tipoPasto; }
}