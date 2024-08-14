package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.Cita;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.CitaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        Optional<Cita> cita = citaService.findById(id);
        return cita.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cita createCita(@RequestBody Cita cita) {
        return citaService.save(cita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita citaDetails) {
        Optional<Cita> cita = citaService.findById(id);
        if (cita.isPresent()) {
            Cita updatedCita = cita.get();
            updatedCita.setMedico(citaDetails.getMedico());
            updatedCita.setPaciente(citaDetails.getPaciente());
            updatedCita.setFechaHora(citaDetails.getFechaHora());
            updatedCita.setMotivo(citaDetails.getMotivo());
            citaService.save(updatedCita);
            return ResponseEntity.ok(updatedCita);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        citaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/medico/{medicoId}")
    public List<Cita> getCitasByMedicoIdAndDateRange(
            @PathVariable Long medicoId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return citaService.findByMedicoIdAndFechaHoraBetween(medicoId, start, end);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<Cita> getCitasByPacienteId(@PathVariable Long pacienteId) {
        return citaService.findByPacienteId(pacienteId);
    }
}
