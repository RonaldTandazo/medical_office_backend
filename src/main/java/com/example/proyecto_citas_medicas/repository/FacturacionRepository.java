package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {
}
