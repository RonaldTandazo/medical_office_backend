package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.LibroDiario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.proyecto_citas_medicas.service.LibroDiarioService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/libro-diario")
public class LibroDiarioController {

    private final LibroDiarioService libroDiarioService;

    public LibroDiarioController(LibroDiarioService libroDiarioService) {
        this.libroDiarioService = libroDiarioService;
    }

    @GetMapping
    public List<LibroDiario> getAllLibroDiario() {
        return libroDiarioService.findAll();
    }

    @GetMapping("/rango-fechas")
    public List<LibroDiario> getLibroDiarioByFechaBetween(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return libroDiarioService.findByFechaBetween(startDate, endDate);
    }
}