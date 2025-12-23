package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.UploadDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UploadDetailRepository extends JpaRepository<UploadDetail, Long> {
    @Query("""
        SELECT COALESCE(SUM(d.total), 0)
        FROM UploadDetail d
        JOIN d.upload u
        WHERE u.status = 'A'
    """)
    Long totalUploadsCurrency();

    @Query("""
        SELECT COALESCE(SUM(d.cantidad), 0)
        FROM UploadDetail d
        JOIN d.upload u
        WHERE u.status = 'A'
    """)
    Long totalInvMedications();

    @Query("""
        SELECT MONTH(u.fecha), SUM(d.total)
        FROM UploadDetail d
        JOIN d.upload u
        WHERE u.status = 'A'
        AND YEAR(u.fecha) = YEAR(CURRENT_DATE)
        GROUP BY MONTH(u.fecha)
        ORDER BY MONTH(u.fecha)
    """)
    List<Object[]> totalAmountByMonth();
}
