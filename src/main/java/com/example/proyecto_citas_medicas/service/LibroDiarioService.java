package service;

import entities.LibroDiario;
import org.springframework.stereotype.Service;
import repository.LibroDiarioRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibroDiarioService {

    private final LibroDiarioRepository libroDiarioRepository;

    public LibroDiarioService(LibroDiarioRepository libroDiarioRepository) {
        this.libroDiarioRepository = libroDiarioRepository;
    }

    public List<LibroDiario> findAll() {
        return libroDiarioRepository.findAll();
    }

    public List<LibroDiario> findByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return libroDiarioRepository.findByFechaBetween(startDate, endDate);
    }
}
