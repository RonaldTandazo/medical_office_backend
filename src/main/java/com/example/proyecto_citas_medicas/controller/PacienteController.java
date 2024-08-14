package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.Paciente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_citas_medicas.service.PacienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteService.getAllPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente) {
        return pacienteService.savePaciente(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Long id, @RequestBody Paciente pacienteDetails) {
        Optional<Paciente> paciente = pacienteService.getPacienteById(id);
        if (paciente.isPresent()) {
            Paciente updatedPaciente = paciente.get();
            updatedPaciente.setNombre(pacienteDetails.getNombre());
            updatedPaciente.setApellido(pacienteDetails.getApellido());
            updatedPaciente.setCedula(pacienteDetails.getCedula());
            updatedPaciente.setDireccion(pacienteDetails.getDireccion());
            updatedPaciente.setTelefono(pacienteDetails.getTelefono());
            pacienteService.savePaciente(updatedPaciente);
            return ResponseEntity.ok(updatedPaciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }
}