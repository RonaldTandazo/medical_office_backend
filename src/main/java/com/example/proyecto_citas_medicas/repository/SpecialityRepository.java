package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
     
}
