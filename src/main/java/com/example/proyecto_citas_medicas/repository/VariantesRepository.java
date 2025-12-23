package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Variantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VariantesRepository extends JpaRepository<Variantes, Long> {

    Optional<Variantes> findByMedicamentoIdAndDescripcionIgnoreCaseAndPresentacionIgnoreCase(
        Long medicamentoId,
        String descripcion,
        String presentacion
    );

    @Query("""
        SELECT new map(
            m.nombre as medicamento,
            v.descripcion as descripcion,
            v.presentacion as presentacion,
            SUM(v.cantidad) as stock
        )
        FROM Variantes v, Medicamentos m
        WHERE m.id = v.medicamentoId
        AND m.status = 'A'
        AND v.status = 'A'
        GROUP BY m.nombre, v.descripcion, v.presentacion
        HAVING SUM(v.cantidad) <= 50
        ORDER BY SUM(v.cantidad) ASC
    """)
    List<Map<String, Object>> lowStockAlerts();
}