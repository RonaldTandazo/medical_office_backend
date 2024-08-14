package service;

import entities.HistoriaClinica;
import org.springframework.stereotype.Service;
import repository.HistoriaClinicaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaClinicaRepository;

    public HistoriaClinicaService(HistoriaClinicaRepository historiaClinicaRepository) {
        this.historiaClinicaRepository = historiaClinicaRepository;
    }

    public List<HistoriaClinica> findAll() {
        return historiaClinicaRepository.findAll();
    }

    public Optional<HistoriaClinica> findById(Long id) {
        return historiaClinicaRepository.findById(id);
    }

    public HistoriaClinica save(HistoriaClinica historiaClinica) {
        return historiaClinicaRepository.save(historiaClinica);
    }

    public void deleteById(Long id) {
        historiaClinicaRepository.deleteById(id);
    }

    public List<HistoriaClinica> findByPacienteId(Long pacienteId) {
        return historiaClinicaRepository.findByPacienteId(pacienteId);
    }

    public List<HistoriaClinica> findByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return historiaClinicaRepository.findByFechaBetween(startDate, endDate);
    }
}
