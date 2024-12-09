package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Doctor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(value = "Select * from doctors where speciality_id = :speciality_id", nativeQuery = true)
    List<Doctor> getDoctorsBySpeciality(@Param("speciality_id") Long speciality_id);

    @Query(value = "Select * from doctors where user_id = :user_id and doctors.status = 'A'", nativeQuery = true)
    Doctor findDoctorByUserId(@Param("user_id") Long user_id); 
}
