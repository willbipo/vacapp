package com.vacapp.ganado.internal.infrastructure.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador Web para servir archivos HTML estáticos.
 */
@Controller
@RequestMapping({"/inventario", "/ganado"})
public class GanadoWebController {

    @GetMapping({"", "/", "/inventario"})
    public String inventario() {
        return "forward:/views/ganado.html";
    }
}
