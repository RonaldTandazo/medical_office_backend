package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.ComprobanteVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComprobanteVentaRepository extends JpaRepository<ComprobanteVenta, Long> {
    List<ComprobanteVenta> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
