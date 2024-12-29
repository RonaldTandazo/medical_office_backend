package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.DoctorPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorPatientRepository extends JpaRepository<DoctorPatient, Long>, JpaSpecificationExecutor<DoctorPatient>  {
    @Query(value = "Insert into doctor_patients (doctor_id, patient_id, status) values (:doctor_id, :patient_id, 'A') Returning doctor_patient_id, doctor_id, patient_id, status", nativeQuery = true)
    DoctorPatient store(@Param("doctor_id") Long doctor_id, @Param("patient_id") Long patient_id);

    @Query(value = "Update doctor_patients set status = 'I' where doctor_id = :doctor_id and patient_id = :patient_id and status = 'A' Returning true", nativeQuery = true)
    boolean unlinkPatient(@Param("patient_id") Long patient_id, @Param("doctor_id") Long doctor_id);
}
