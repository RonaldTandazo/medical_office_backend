package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.LibroDiario;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.LibroDiarioRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibroDiarioService {

    private final LibroDiarioRepository libroDiarioRepository;

    public LibroDiarioService(LibroDiarioRepository libroDiarioRepository) {
        this.libroDiarioRepository = libroDiarioRepository;
    }

    public List<LibroDiario> findAll() {
        return libroDiarioRepository.findAll();
    }

    public List<LibroDiario> findByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return libroDiarioRepository.findByFechaBetween(startDate, endDate);
    }
}
