package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.LibroDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LibroDiarioRepository extends JpaRepository<LibroDiario, Long> {
    List<LibroDiario> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
