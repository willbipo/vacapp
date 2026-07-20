package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PotreroRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    String nombre,
    
    String seccionId,
    
    @DecimalMin(value = "0.01", message = "Las hectáreas deben ser mayor a 0")
    Double hectareas,
    
    @Size(max = 100, message = "El tipo de pasto debe tener máximo 100 caracteres")
    String tipoPasto
) {
}
