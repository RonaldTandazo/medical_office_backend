package com.example.proyecto_citas_medicas.service;

import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.PacienteRepository;
import java.util.List;
import java.util.Map;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Map<String, Object>> getPacientesByDoctor(Long doctor_id) {
        return pacienteRepository.findPatientsByDoctorId(doctor_id);
    }
}
