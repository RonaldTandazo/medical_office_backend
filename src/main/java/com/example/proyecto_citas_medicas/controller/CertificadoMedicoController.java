package controller;

import entities.CertificadoMedico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CertificadoMedicoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/certificados-medicos")
public class CertificadoMedicoController {

    private final CertificadoMedicoService certificadoMedicoService;

    public CertificadoMedicoController(CertificadoMedicoService certificadoMedicoService) {
        this.certificadoMedicoService = certificadoMedicoService;
    }

    @GetMapping
    public List<CertificadoMedico> getAllCertificadosMedicos() {
        return certificadoMedicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificadoMedico> getCertificadoMedicoById(@PathVariable Long id) {
        Optional<CertificadoMedico> certificadoMedico = certificadoMedicoService.findById(id);
        return certificadoMedico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CertificadoMedico createCertificadoMedico(@RequestBody CertificadoMedico certificadoMedico) {
        return certificadoMedicoService.save(certificadoMedico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificadoMedico> updateCertificadoMedico(@PathVariable Long id, @RequestBody CertificadoMedico certificadoMedicoDetails) {
        Optional<CertificadoMedico> certificadoMedico = certificadoMedicoService.findById(id);
        if (certificadoMedico.isPresent()) {
            CertificadoMedico updatedCertificadoMedico = certificadoMedico.get();
            updatedCertificadoMedico.setMedico(certificadoMedicoDetails.getMedico());
            updatedCertificadoMedico.setPaciente(certificadoMedicoDetails.getPaciente());
            updatedCertificadoMedico.setFechaEmision(certificadoMedicoDetails.getFechaEmision());
            updatedCertificadoMedico.setDescripcion(certificadoMedicoDetails.getDescripcion());
            updatedCertificadoMedico.setObservaciones(certificadoMedicoDetails.getObservaciones());
            certificadoMedicoService.save(updatedCertificadoMedico);
            return ResponseEntity.ok(updatedCertificadoMedico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificadoMedico(@PathVariable Long id) {
        certificadoMedicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<CertificadoMedico> getCertificadosMedicosByPacienteId(@PathVariable Long pacienteId) {
        return certificadoMedicoService.findByPacienteId(pacienteId);
    }

    @GetMapping("/medico/{medicoId}")
    public List<CertificadoMedico> getCertificadosMedicosByMedicoId(@PathVariable Long medicoId) {
        return certificadoMedicoService.findByMedicoId(medicoId);
    }
}