package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.HistoriaClinica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.HistoriaClinicaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historia-clinica")
public class HistoriaClinicaController {

    private final HistoriaClinicaService historiaClinicaService;

    public HistoriaClinicaController(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    @GetMapping
    public List<HistoriaClinica> getAllHistoriaClinica() {
        return historiaClinicaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinica> getHistoriaClinicaById(@PathVariable Long id) {
        Optional<HistoriaClinica> historiaClinica = historiaClinicaService.findById(id);
        return historiaClinica.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistoriaClinica createHistoriaClinica(@RequestBody HistoriaClinica historiaClinica) {
        return historiaClinicaService.save(historiaClinica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinica> updateHistoriaClinica(@PathVariable Long id, @RequestBody HistoriaClinica historiaClinicaDetails) {
        Optional<HistoriaClinica> historiaClinica = historiaClinicaService.findById(id);
        if (historiaClinica.isPresent()) {
            HistoriaClinica updatedHistoriaClinica = historiaClinica.get();
            updatedHistoriaClinica.setPaciente(historiaClinicaDetails.getPaciente());
            updatedHistoriaClinica.setFecha(historiaClinicaDetails.getFecha());
            updatedHistoriaClinica.setDescripcion(historiaClinicaDetails.getDescripcion());
            historiaClinicaService.save(updatedHistoriaClinica);
            return ResponseEntity.ok(updatedHistoriaClinica);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoriaClinica(@PathVariable Long id) {
        historiaClinicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<HistoriaClinica> getHistoriaClinicaByPacienteId(@PathVariable Long pacienteId) {
        return historiaClinicaService.findByPacienteId(pacienteId);
    }

    @GetMapping("/rango-fechas")
    public List<HistoriaClinica> getHistoriaClinicaByFechaBetween(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        return historiaClinicaService.findByFechaBetween(startDate, endDate);
    }
}
