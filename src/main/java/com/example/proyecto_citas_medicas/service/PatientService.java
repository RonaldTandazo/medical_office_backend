package com.example.proyecto_citas_medicas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.proyecto_citas_medicas.entities.Patient;
import com.example.proyecto_citas_medicas.repository.PatientRepository;
import com.example.proyecto_citas_medicas.specifications.PatientSpecification;

import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Page</*Map<String, Object>*/Patient> getPacientesByDoctor(Long user_id, String identification, String patientName, Character gender, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(PatientSpecification.getPacientesByDoctor(user_id, identification, patientName, gender), pageable);
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
        patient.setDisease(NewData.getDisease());

        return patientRepository.save(patient);
    }
}