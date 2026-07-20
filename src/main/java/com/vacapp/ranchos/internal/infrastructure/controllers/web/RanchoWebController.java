package com.vacapp.ranchos.internal.infrastructure.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador Web para servir archivos HTML estáticos.
 */
@Controller
@RequestMapping("/ranchos")
public class RanchoWebController {

    @GetMapping({"", "/", "/inventario"})
    public String ranchos() {
        return "forward:/views/ranchos.html";
    }
}
