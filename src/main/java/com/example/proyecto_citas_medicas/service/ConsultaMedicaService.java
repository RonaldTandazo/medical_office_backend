package service;

import entities.ConsultaMedica;
import org.springframework.stereotype.Service;
import repository.ConsultaMedicaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaMedicaService {

    private final ConsultaMedicaRepository consultaMedicaRepository;

    public ConsultaMedicaService(ConsultaMedicaRepository consultaMedicaRepository) {
        this.consultaMedicaRepository = consultaMedicaRepository;
    }

    public List<ConsultaMedica> findAll() {
        return consultaMedicaRepository.findAll();
    }

    public Optional<ConsultaMedica> findById(Long id) {
        return consultaMedicaRepository.findById(id);
    }

    public ConsultaMedica save(ConsultaMedica consultaMedica) {
        return consultaMedicaRepository.save(consultaMedica);
    }

    public void deleteById(Long id) {
        consultaMedicaRepository.deleteById(id);
    }

    public List<ConsultaMedica> findByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime start, LocalDateTime end) {
        return consultaMedicaRepository.findByMedicoIdAndFechaHoraBetween(medicoId, start, end);
    }

    public List<ConsultaMedica> findByPacienteId(Long pacienteId) {
        return consultaMedicaRepository.findByPacienteId(pacienteId);
    }
}
