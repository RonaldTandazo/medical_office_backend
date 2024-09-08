package com.example.proyecto_citas_medicas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.PacienteRepository;
import java.util.Map;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Page<Map<String, Object>> getPacientesByDoctor(Long doctor_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pacienteRepository.findPatientsByDoctorId(doctor_id, pageable);
    }
}
