package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
