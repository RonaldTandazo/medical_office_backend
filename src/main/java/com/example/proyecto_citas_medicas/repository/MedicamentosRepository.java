package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MedicamentosRepository extends JpaRepository<Medicamentos, Long> {

    Optional<Medicamentos> findByNombreIgnoreCase(String nombre);

    @Query("""
        SELECT COUNT(*)
        FROM Medicamentos m
        WHERE m.status = 'A'
    """)
    Long totalMedications();
}
