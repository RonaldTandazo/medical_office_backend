package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @Query(value = "Select * from medicos where user_id = :user_id", nativeQuery = true)
    Medico findMedicoByUserId(@Param("user_id") Long user_id); 
}
