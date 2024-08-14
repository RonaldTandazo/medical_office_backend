package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.TransaccionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<Transaccion> getAllTransacciones() {
        return transaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> getTransaccionById(@PathVariable Long id) {
        Optional<Transaccion> transaccion = transaccionService.findById(id);
        return transaccion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transaccion createTransaccion(@RequestBody Transaccion transaccion) {
        return transaccionService.save(transaccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> updateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccionDetails) {
        Optional<Transaccion> transaccion = transaccionService.findById(id);
        if (transaccion.isPresent()) {
            Transaccion updatedTransaccion = transaccion.get();
            updatedTransaccion.setPaciente(transaccionDetails.getPaciente());
            updatedTransaccion.setTipo(transaccionDetails.getTipo());
            updatedTransaccion.setMonto(transaccionDetails.getMonto());
            updatedTransaccion.setFecha(transaccionDetails.getFecha());
            updatedTransaccion.setDescripcion(transaccionDetails.getDescripcion());
            transaccionService.save(updatedTransaccion);
            return ResponseEntity.ok(updatedTransaccion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable Long id) {
        transaccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<Transaccion> getTransaccionesByPacienteId(@PathVariable Long pacienteId) {
        return transaccionService.findByPacienteId(pacienteId);
    }

    @GetMapping("/rango-fechas")
    public List<Transaccion> getTransaccionesByFechaBetween(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return transaccionService.findByFechaBetween(startDate, endDate);
    }
}
