package com.example.proyecto_citas_medicas.service;

import org.springframework.stereotype.Service;

import com.example.proyecto_citas_medicas.entities.Appointment;
import com.example.proyecto_citas_medicas.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment store(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
}
