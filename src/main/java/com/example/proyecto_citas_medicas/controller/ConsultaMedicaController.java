package controller;

import entities.ConsultaMedica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ConsultaMedicaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas-medicas")
public class ConsultaMedicaController {

    private final ConsultaMedicaService consultaMedicaService;

    public ConsultaMedicaController(ConsultaMedicaService consultaMedicaService) {
        this.consultaMedicaService = consultaMedicaService;
    }

    @GetMapping
    public List<ConsultaMedica> getAllConsultasMedicas() {
        return consultaMedicaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaMedica> getConsultaMedicaById(@PathVariable Long id) {
        Optional<ConsultaMedica> consultaMedica = consultaMedicaService.findById(id);
        return consultaMedica.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ConsultaMedica createConsultaMedica(@RequestBody ConsultaMedica consultaMedica) {
        return consultaMedicaService.save(consultaMedica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaMedica> updateConsultaMedica(@PathVariable Long id, @RequestBody ConsultaMedica consultaMedicaDetails) {
        Optional<ConsultaMedica> consultaMedica = consultaMedicaService.findById(id);
        if (consultaMedica.isPresent()) {
            ConsultaMedica updatedConsultaMedica = consultaMedica.get();
            updatedConsultaMedica.setMedico(consultaMedicaDetails.getMedico());
            updatedConsultaMedica.setPaciente(consultaMedicaDetails.getPaciente());
            updatedConsultaMedica.setFechaHora(consultaMedicaDetails.getFechaHora());
            updatedConsultaMedica.setDiagnostico(consultaMedicaDetails.getDiagnostico());
            updatedConsultaMedica.setTratamiento(consultaMedicaDetails.getTratamiento());
            consultaMedicaService.save(updatedConsultaMedica);
            return ResponseEntity.ok(updatedConsultaMedica);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultaMedica(@PathVariable Long id) {
        consultaMedicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/medico/{medicoId}")
    public List<ConsultaMedica> getConsultasMedicasByMedicoIdAndDateRange(
            @PathVariable Long medicoId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return consultaMedicaService.findByMedicoIdAndFechaHoraBetween(medicoId, start, end);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<ConsultaMedica> getConsultasMedicasByPacienteId(@PathVariable Long pacienteId) {
        return consultaMedicaService.findByPacienteId(pacienteId);
    }
}
