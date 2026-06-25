package com.riwi.talentboard.controller;

import com.riwi.talentboard.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Usamos @Controller en lugar de @RestController para retornar VISTAS html
@RequiredArgsConstructor
public class ViewController {

    private final VacancyService vacancyService;

    // Ruta raíz o dashboard que cargará la plantilla index.html
    @GetMapping("/")
    public String showDashboard(Model model) {
        // Obtenemos las vacantes directamente de la base de datos a través del servicio
        // y las inyectamos al modelo de Thymeleaf
        model.addAttribute("vacancies", vacancyService.getAll());

        // Retorna el nombre del archivo html sin la extensión (templates/index.html)
        return "index";
    }
}