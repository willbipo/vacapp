package com.vacapp.ranchos.internal.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Seccion {
    private final String id;
    private final String ranchoId; 
    private String nombre;
    private final List<Potrero> potreros;

    public Seccion(String id, String ranchoId, String nombre) {
        this.id = id;
        this.ranchoId = ranchoId;
        this.nombre = nombre;
        this.potreros = new ArrayList<>();
    }

    public Seccion(String id, String ranchoId, String nombre, List<Potrero> potreros) {
        this.id = id;
        this.ranchoId = ranchoId;
        this.nombre = nombre;
        this.potreros = new ArrayList<>(potreros);
    }

    // Regla de Negocio: Agregar un potrero a esta sección
    public void agregarPotrero(Potrero potrero) {
        this.potreros.add(potrero);
    }

    // Calcular el total de hectáreas de esta sección sumando sus potreros
    public Double calcularHectareasTotales() {
        return potreros.stream()
                .mapToDouble(Potrero::getHectareas)
                .sum();
    }

    // Getters
    public String getId() { return id; }
    public String getRanchoId() { return ranchoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Potrero> getPotreros() { return new ArrayList<>(potreros); } // Retornar copia para proteger inmutabilidad
}