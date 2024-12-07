package com.example.proyecto_citas_medicas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.Medication;
import com.example.proyecto_citas_medicas.repository.MedicationRepository;
import com.example.proyecto_citas_medicas.specifications.MedicationSpecification;
import java.util.Map;
import java.util.Optional;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Page</*Map<String, Object>*/Medication> getMedicationsByName(String medication, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return medicationRepository.findAll(MedicationSpecification.getMedicationByName(medication), pageable);
    }
}
