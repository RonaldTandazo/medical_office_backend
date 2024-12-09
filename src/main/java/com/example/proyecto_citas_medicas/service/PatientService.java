package com.example.proyecto_citas_medicas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.Patient;
import com.example.proyecto_citas_medicas.repository.PatientRepository;
import com.example.proyecto_citas_medicas.specifications.PatientSpecification;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientSpecification patientSpecification;

    public PatientService(PatientRepository patientRepository, PatientSpecification patientSpecification) {
        this.patientRepository = patientRepository;
        this.patientSpecification = patientSpecification;
    }

    public Page</*Map<String, Object>*/Patient> getPacientesByDoctor(Long doctorId, String identification, String patientName, Character gender, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Patient> spec = this.patientSpecification.getPacientesByDoctor(doctorId, identification, patientName, gender);

        return patientRepository.findAll(spec, pageable);
    }

    public Patient store(Patient patient){
        return patientRepository.save(patient);
    }

    public Optional<Patient> findById(Long patient_id){
        return patientRepository.findById(patient_id);
    }

    public Patient update(Patient NewData, Patient patient){
        patient.setName(NewData.getName());
        patient.setLastname(NewData.getLastname());
        patient.setIdentification(NewData.getIdentification());
        patient.setAge(NewData.getAge());
        patient.setWeight(NewData.getWeight());
        patient.setHeight(NewData.getHeight());
        patient.setGender(NewData.getGender());
        patient.setPhone(NewData.getPhone());
        patient.setDirection(NewData.getDirection());
        patient.setEmail(NewData.getEmail());
        
        return patientRepository.save(patient);
    }
}
