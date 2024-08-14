package service;

import entities.Facturacion;
import org.springframework.stereotype.Service;
import repository.FacturacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacturacionService {

    private final FacturacionRepository facturacionRepository;

    public FacturacionService(FacturacionRepository facturacionRepository) {
        this.facturacionRepository = facturacionRepository;
    }

    public List<Facturacion> getAllFacturaciones() {
        return facturacionRepository.findAll();
    }

    public Optional<Facturacion> getFacturacionById(Long id) {
        return facturacionRepository.findById(id);
    }

    public Facturacion saveFacturacion(Facturacion facturacion) {
        return facturacionRepository.save(facturacion);
    }

    public void deleteFacturacion(Long id) {
        facturacionRepository.deleteById(id);
    }
}
