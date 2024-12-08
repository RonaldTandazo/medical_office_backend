package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.MedicationLaboratory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationLaboratoryRepository extends JpaRepository<MedicationLaboratory, Long> {
    @Query(value = "SELECT distinct laboratories.laboratory_id, laboratories.name FROM medication_laboratories "
    +"Join laboratories on medication_laboratories.laboratory_id = laboratories.laboratory_id and laboratories.status = 'A'"  
    +"WHERE medication_laboratories.medication_id = :medication_id and medication_laboratories.status = 'A'", nativeQuery = true)  
    List<Object[]> getLaboratoriesFromMedication(@Param("medication_id") Long medication_id);

    @Query(value = "SELECT medication_laboratories.medication_laboratory_id, medication_laboratories.grams, medication_laboratories.price, medication_laboratories.unity FROM medication_laboratories "
    +"WHERE medication_laboratories.medication_id = :medication_id and medication_laboratories.laboratory_id = :laboratory_id and medication_laboratories.status = 'A'", nativeQuery = true)  
    List<Object[]> getInfoMedicationLaboratory(@Param("medication_id") Long medication_id, @Param("laboratory_id") Long laboratory_id);
}
