package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Uploads;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadsRepository extends JpaRepository<Uploads, Long> {
    @Query("""
        SELECT DISTINCT u
        FROM Uploads u
        LEFT JOIN FETCH u.details d
        WHERE u.status = 'A'
        ORDER BY u.fecha DESC
    """)
    List<Uploads> findAllActiveWithDetails();

    @Query("""
        SELECT COUNT(*)
        FROM Uploads u
        WHERE u.status = 'A'
    """)
    Long totalUploads();

    @Query("""
        SELECT MONTH(u.fecha), COUNT(u.id)
        FROM Uploads u
        WHERE YEAR(u.fecha) = YEAR(CURRENT_DATE)
        AND u.status = 'A'
        GROUP BY MONTH(u.fecha)
        ORDER BY MONTH(u.fecha)
    """)
    List<Object[]> uploadsByMonth();
}
