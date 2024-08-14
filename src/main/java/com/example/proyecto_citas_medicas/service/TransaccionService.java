package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.Transaccion;
import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.repository.TransaccionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    public Optional<Transaccion> findById(Long id) {
        return transaccionRepository.findById(id);
    }

    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    public void deleteById(Long id) {
        transaccionRepository.deleteById(id);
    }

    public List<Transaccion> findByPacienteId(Long pacienteId) {
        return transaccionRepository.findByPacienteId(pacienteId);
    }

    public List<Transaccion> findByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return transaccionRepository.findByFechaBetween(startDate, endDate);
    }
}
