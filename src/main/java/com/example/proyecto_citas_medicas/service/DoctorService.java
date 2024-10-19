package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.Doctor;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getDoctorsBySpeciality(Long speciality_id){
        return doctorRepository.getDoctorsBySpeciality(speciality_id);
    }

    // public Doctor findDoctorByUserId(Long user_id){
    //     return doctorRepository.findMedicoByUserId(user_id);
    // }

    // public List<Doctor> findAll() {
    //     return doctorRepository.findAll();
    // }

    // public Optional<Doctor> findById(Long id) {
    //     return doctorRepository.findById(id);
    // }

    // public Doctor save(Doctor medico) {
    //     return doctorRepository.save(medico);
    // }

    // public void deleteById(Long id) {
    //     doctorRepository.deleteById(id);
    // }
}
