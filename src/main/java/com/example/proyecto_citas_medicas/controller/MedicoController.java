package controller;

import entities.Medico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MedicoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public List<Medico> getAllMedicos() {
        return medicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable Long id) {
        Optional<Medico> medico = medicoService.findById(id);
        return medico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medico createMedico(@RequestBody Medico medico) {
        return medicoService.save(medico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> updateMedico(@PathVariable Long id, @RequestBody Medico medicoDetails) {
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isPresent()) {
            Medico updatedMedico = medico.get();
            updatedMedico.setNombre(medicoDetails.getNombre());
            updatedMedico.setEspecialidad(medicoDetails.getEspecialidad());
            updatedMedico.setEmail(medicoDetails.getEmail());
            updatedMedico.setTelefono(medicoDetails.getTelefono());
            medicoService.save(updatedMedico);
            return ResponseEntity.ok(updatedMedico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        medicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
