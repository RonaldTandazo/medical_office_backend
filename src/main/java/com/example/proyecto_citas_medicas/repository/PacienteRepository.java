package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Paciente;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query(value = "Select * from pacientes where doctor_id = :doctor_id", nativeQuery = true)
    List<Map<String, Object>> findPatientsByDoctorId(@Param("doctor_id") Long doctor_id);
}
