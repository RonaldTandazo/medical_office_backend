package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    /*@Query(value = "SELECT * FROM patients WHERE doctor_id = :user_id",
        countQuery = "SELECT count(*) FROM patients WHERE doctor_id = :user_id", 
        nativeQuery = true)    
    Page<Map<String, Object>> findPatientsByDoctorId(@Param("user_id") Long user_id, Pageable pageable);*/
}
