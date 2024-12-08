package com.example.proyecto_citas_medicas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.Medication;
import com.example.proyecto_citas_medicas.repository.MedicationLaboratoryRepository;
import com.example.proyecto_citas_medicas.repository.MedicationRepository;
import com.example.proyecto_citas_medicas.specifications.MedicationSpecification;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationLaboratoryRepository medicationLaboratoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(MedicationService.class);

    public MedicationService(MedicationRepository medicationRepository, MedicationLaboratoryRepository medicationLaboratoryRepository) {
        this.medicationRepository = medicationRepository;
        this.medicationLaboratoryRepository = medicationLaboratoryRepository;
    }

    public Map<String, Object> getMedicationsByName(String medicationName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        // Paso 1: Filtrar medicamentos
        Specification<Medication> spec = MedicationSpecification.medicationByNameAndStatus(medicationName);
    
        // Paso 2: Obtener los medicamentos con paginación
        Page<Medication> medicationsPage = medicationRepository.findAll(spec, pageable);
    
        // Estructura para la respuesta
        List<Map<String, Object>> dataMedications = new ArrayList<>();
    
        for (Medication medication : medicationsPage) {
            Long medication_id = medication.getMedicationId();
    
            // Obtener laboratorios asociados
            List<Object[]> laboratories = medicationLaboratoryRepository.getLaboratoriesFromMedication(medication_id);
    
            // Si no hay laboratorios, descartar este medicamento
            if (laboratories == null || laboratories.isEmpty()) {
                continue;
            }
    
            List<Map<String, Object>> laboratoryDataList = new ArrayList<>();
            boolean hasValidLaboratoryDetails = false;
    
            // Verificar cada laboratorio asociado
            for (Object[] laboratory : laboratories) {
                Long laboratory_id = (Long) laboratory[0];
                String laboratory_name = (String) laboratory[1];
    
                // Obtener los detalles de la relación entre el medicamento y el laboratorio
                List<Object[]> medicationDetail = medicationLaboratoryRepository.getInfoMedicationLaboratory(medication_id, laboratory_id);
    
                // Si no hay detalles de la relación, saltar este laboratorio
                if (medicationDetail == null || medicationDetail.isEmpty()) {
                    continue;
                }
    
                // Construir la información del laboratorio
                Map<String, Object> laboratoryData = new HashMap<>();
                laboratoryData.put("laboratory_id", laboratory_id);
                laboratoryData.put("laboratory", laboratory_name);
    
                List<Map<String, Object>> medicationDetails = new ArrayList<>();
                for (Object[] info : medicationDetail) {
                    Map<String, Object> medicationDetailData = new HashMap<>();
                    medicationDetailData.put("medication_laboratory_id", info[0]);
                    medicationDetailData.put("grams", info[1]);
                    medicationDetailData.put("price", info[2]);
                    medicationDetailData.put("unit", info[3]);
                    medicationDetails.add(medicationDetailData);
                }
    
                laboratoryData.put("content", medicationDetails);
                laboratoryDataList.add(laboratoryData);
    
                // Marcar que al menos un laboratorio tiene detalles válidos
                hasValidLaboratoryDetails = true;
            }
    
            // Si no hay laboratorios válidos con detalles, descartar este medicamento
            if (!hasValidLaboratoryDetails) {
                continue;
            }
    
            // Construir la información del medicamento
            Map<String, Object> medicationData = new HashMap<>();
            medicationData.put("medication_id", medication.getMedicationId());
            medicationData.put("medication", medication.getName());
            medicationData.put("type", medication.getType());
            medicationData.put("diseases", medication.getDiseases());
            medicationData.put("laboratories", laboratoryDataList);
    
            // Almacenar el medicamento con toda su información en la lista
            dataMedications.add(medicationData);
        }
    
        // Estructura de respuesta final
        Map<String, Object> response = new HashMap<>();
        response.put("medications", dataMedications);
        response.put("pagination", Map.of(
            "totalElements", medicationsPage.getTotalElements(),
            "totalPages", medicationsPage.getTotalPages(),
            "currentPage", medicationsPage.getNumber(),
            "pageSize", medicationsPage.getSize()
        ));
    
        logger.info("Response data: {}", response);
    
        return response;
    }        
}
