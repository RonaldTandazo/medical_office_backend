package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ComprobanteVenta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.ComprobanteVentaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comprobantes-venta")
public class ComprobanteVentaController {

    private final ComprobanteVentaService comprobanteVentaService;

    public ComprobanteVentaController(ComprobanteVentaService comprobanteVentaService) {
        this.comprobanteVentaService = comprobanteVentaService;
    }

    @GetMapping
    public List<ComprobanteVenta> getAllComprobantesVenta() {
        return comprobanteVentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteVenta> getComprobanteVentaById(@PathVariable Long id) {
        Optional<ComprobanteVenta> comprobanteVenta = comprobanteVentaService.findById(id);
        return comprobanteVenta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ComprobanteVenta createComprobanteVenta(@RequestBody ComprobanteVenta comprobanteVenta) {
        return comprobanteVentaService.save(comprobanteVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComprobanteVenta> updateComprobanteVenta(@PathVariable Long id, @RequestBody ComprobanteVenta comprobanteVentaDetails) {
        Optional<ComprobanteVenta> comprobanteVenta = comprobanteVentaService.findById(id);
        if (comprobanteVenta.isPresent()) {
            ComprobanteVenta updatedComprobanteVenta = comprobanteVenta.get();
            updatedComprobanteVenta.setNumero(comprobanteVentaDetails.getNumero());
            updatedComprobanteVenta.setFecha(comprobanteVentaDetails.getFecha());
            updatedComprobanteVenta.setMontoTotal(comprobanteVentaDetails.getMontoTotal());
            comprobanteVentaService.save(updatedComprobanteVenta);
            return ResponseEntity.ok(updatedComprobanteVenta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComprobanteVenta(@PathVariable Long id) {
        comprobanteVentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rango-fechas")
    public List<ComprobanteVenta> getComprobanteVentaByFechaBetween(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return comprobanteVentaService.findByFechaBetween(startDate, endDate);
    }
}
