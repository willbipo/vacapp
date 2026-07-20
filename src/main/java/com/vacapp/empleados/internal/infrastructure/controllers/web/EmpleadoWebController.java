package com.vacapp.empleados.internal.infrastructure.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador Web para servir archivos HTML estáticos.
 */
@Controller
@RequestMapping("/empleados")
public class EmpleadoWebController {

    @GetMapping({"", "/", "/inventario"})
    public String listar() {
        return "forward:/views/empleados.html";
    }
}
