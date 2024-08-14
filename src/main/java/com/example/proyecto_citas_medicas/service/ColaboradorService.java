package service;

import entities.Colaborador;
import org.springframework.stereotype.Service;
import repository.ColaboradorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public List<Colaborador> findAll() {
        return colaboradorRepository.findAll();
    }

    public Optional<Colaborador> findById(Long id) {
        return colaboradorRepository.findById(id);
    }

    public Colaborador save(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public void deleteById(Long id) {
        colaboradorRepository.deleteById(id);
    }
}
