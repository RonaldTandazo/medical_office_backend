package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    List<HistoriaClinica> findByPacienteId(Long pacienteId);
    List<HistoriaClinica> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
