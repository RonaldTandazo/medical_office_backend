package service;

import entities.Cita;
import org.springframework.stereotype.Service;
import repository.CitaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    public Optional<Cita> findById(Long id) {
        return citaRepository.findById(id);
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }

    public List<Cita> findByMedicoIdAndFechaHoraBetween(Long medicoId, LocalDateTime start, LocalDateTime end) {
        return citaRepository.findByMedicoIdAndFechaHoraBetween(medicoId, start, end);
    }

    public List<Cita> findByPacienteId(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }
}
