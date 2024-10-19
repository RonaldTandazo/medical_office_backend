package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.Speciality;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.SpecialityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }
}
