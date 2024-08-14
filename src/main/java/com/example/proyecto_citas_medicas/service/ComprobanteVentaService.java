package service;

import entities.ComprobanteVenta;
import org.springframework.stereotype.Service;
import repository.ComprobanteVentaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteVentaService {

    private final ComprobanteVentaRepository comprobanteVentaRepository;

    public ComprobanteVentaService(ComprobanteVentaRepository comprobanteVentaRepository) {
        this.comprobanteVentaRepository = comprobanteVentaRepository;
    }

    public List<ComprobanteVenta> findAll() {
        return comprobanteVentaRepository.findAll();
    }

    public Optional<ComprobanteVenta> findById(Long id) {
        return comprobanteVentaRepository.findById(id);
    }

    public ComprobanteVenta save(ComprobanteVenta comprobanteVenta) {
        return comprobanteVentaRepository.save(comprobanteVenta);
    }

    public void deleteById(Long id) {
        comprobanteVentaRepository.deleteById(id);
    }

    public List<ComprobanteVenta> findByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return comprobanteVentaRepository.findByFechaBetween(startDate, endDate);
    }
}