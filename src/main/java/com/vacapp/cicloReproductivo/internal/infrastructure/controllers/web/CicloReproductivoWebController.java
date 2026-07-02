package com.vacapp.cicloReproductivo.internal.infrastructure.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controlador web que sirve la vista del ciclo reproductivo. */
@Controller
public class CicloReproductivoWebController {

    @GetMapping("/ciclo-reproductivo")
    public String cicloReproductivo() {
        return "cicloReproductivo/ciclo-reproductivo";
    }
}
