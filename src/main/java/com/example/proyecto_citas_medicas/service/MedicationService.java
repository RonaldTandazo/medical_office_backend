package com.example.proyecto_citas_medicas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final MedicationSpecification medicationSpecification;

    public MedicationService(MedicationRepository medicationRepository, MedicationLaboratoryRepository medicationLaboratoryRepository, MedicationSpecification medicationSpecification) {
        this.medicationRepository = medicationRepository;
        this.medicationLaboratoryRepository = medicationLaboratoryRepository;
        this.medicationSpecification = medicationSpecification;
    }

    public Map<String, Object> getMedicationsByName(String medicationName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
    
        Specification<Medication> spec = this.medicationSpecification.medicationByNameAndStatus(medicationName);
    
        Page<Medication> medicationsPage = medicationRepository.findAll(spec, pageable);
    
        List<Map<String, Object>> dataMedications = new ArrayList<>();
    
        for (Medication medication : medicationsPage) {
            Long medication_id = medication.getMedicationId();
    
            List<Object[]> laboratories = medicationLaboratoryRepository.getLaboratoriesFromMedication(medication_id);
    
            if (laboratories == null || laboratories.isEmpty()) {
                continue;
            }
    
            List<Map<String, Object>> laboratoryDataList = new ArrayList<>();
            boolean hasValidLaboratoryDetails = false;
    
            for (Object[] laboratory : laboratories) {
                Long laboratory_id = (Long) laboratory[0];
                String laboratory_name = (String) laboratory[1];
    
                List<Object[]> medicationDetail = medicationLaboratoryRepository.getInfoMedicationLaboratory(medication_id, laboratory_id);
    
                if (medicationDetail == null || medicationDetail.isEmpty()) {
                    continue;
                }
    
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
    
                hasValidLaboratoryDetails = true;
            }
    
            if (!hasValidLaboratoryDetails) {
                continue;
            }
    
            Map<String, Object> medicationData = new HashMap<>();
            medicationData.put("medication_id", medication.getMedicationId());
            medicationData.put("medication", medication.getName());
            medicationData.put("type", medication.getType());
            medicationData.put("diseases", medication.getDiseases());
            medicationData.put("laboratories", laboratoryDataList);
    
            dataMedications.add(medicationData);
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("medications", dataMedications);
        response.put("pagination", Map.of(
            "totalElements", medicationsPage.getTotalElements(),
            "totalPages", medicationsPage.getTotalPages(),
            "currentPage", medicationsPage.getNumber(),
            "pageSize", medicationsPage.getSize()
        ));
        
        return response;
    }        
}
