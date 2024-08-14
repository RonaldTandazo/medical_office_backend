package controller;

import entities.Facturacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FacturacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturaciones")
public class FacturacionController {

    private final FacturacionService facturacionService;

    public FacturacionController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    @GetMapping
    public List<Facturacion> getAllFacturaciones() {
        return facturacionService.getAllFacturaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facturacion> getFacturacionById(@PathVariable Long id) {
        Optional<Facturacion> facturacion = facturacionService.getFacturacionById(id);
        return facturacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Facturacion createFacturacion(@RequestBody Facturacion facturacion) {
        return facturacionService.saveFacturacion(facturacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facturacion> updateFacturacion(@PathVariable Long id, @RequestBody Facturacion facturacionDetails) {
        Optional<Facturacion> facturacion = facturacionService.getFacturacionById(id);
        if (facturacion.isPresent()) {
            Facturacion updatedFacturacion = facturacion.get();
            updatedFacturacion.setNombreFacturado(facturacionDetails.getNombreFacturado());
            updatedFacturacion.setDireccionFacturacion(facturacionDetails.getDireccionFacturacion());
            updatedFacturacion.setTelefonoFacturacion(facturacionDetails.getTelefonoFacturacion());
            updatedFacturacion.setCedulaFacturacion(facturacionDetails.getCedulaFacturacion());
            facturacionService.saveFacturacion(updatedFacturacion);
            return ResponseEntity.ok(updatedFacturacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacturacion(@PathVariable Long id) {
        facturacionService.deleteFacturacion(id);
        return ResponseEntity.noContent().build();
    }
}
