package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.Doctor;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.DoctorPatientRepository;
import com.example.proyecto_citas_medicas.repository.DoctorRepository;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorPatientRepository doctorPatientRepository;

    public DoctorService(DoctorRepository doctorRepository, DoctorPatientRepository doctorPatientRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorPatientRepository = doctorPatientRepository;
    }

    public List<Doctor> getDoctorsBySpeciality(Long speciality_id){
        return doctorRepository.getDoctorsBySpeciality(speciality_id);
    }

    public Doctor findDoctorByUserId(Long user_id){
        return doctorRepository.findDoctorByUserId(user_id);
    }

    public boolean unlinkPatient(Long patient_id, Long doctor_id){
        return doctorPatientRepository.unlinkPatient(patient_id, doctor_id);
    }

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
