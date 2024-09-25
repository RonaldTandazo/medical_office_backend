package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Patient;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Patient, Long> {

    @Query(value = "SELECT * FROM patients WHERE doctor_id = :doctor_id",
        countQuery = "SELECT count(*) FROM patients WHERE doctor_id = :doctor_id", 
        nativeQuery = true)    
    Page<Map<String, Object>> findPatientsByDoctorId(@Param("doctor_id") Long doctor_id, Pageable pageable);
}
