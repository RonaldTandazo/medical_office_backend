package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByPacienteId(Long pacienteId);
    List<Transaccion> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
