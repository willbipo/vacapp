package com.vacapp.calendario.internal.infrastructure.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controlador web que sirve la vista del calendario. */
@Controller
public class CalendarioWebController {

    /** GET /calendario → templates/calendario/calendario.html */
    @GetMapping("/calendario")
    public String calendario() {
        return "calendario/calendario";
    }
}
