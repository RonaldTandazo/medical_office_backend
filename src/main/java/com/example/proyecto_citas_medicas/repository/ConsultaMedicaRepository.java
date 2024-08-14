package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.ConsultaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedica, Long> {
    List<ConsultaMedica> findByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime start, LocalDateTime end);
    List<ConsultaMedica> findByPacienteId(Long pacienteId);
}
