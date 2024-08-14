package service;

import entities.CertificadoMedico;
import org.springframework.stereotype.Service;
import repository.CertificadoMedicoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CertificadoMedicoService {

    private final CertificadoMedicoRepository certificadoMedicoRepository;

    public CertificadoMedicoService(CertificadoMedicoRepository certificadoMedicoRepository) {
        this.certificadoMedicoRepository = certificadoMedicoRepository;
    }

    public List<CertificadoMedico> findAll() {
        return certificadoMedicoRepository.findAll();
    }

    public Optional<CertificadoMedico> findById(Long id) {
        return certificadoMedicoRepository.findById(id);
    }

    public CertificadoMedico save(CertificadoMedico certificadoMedico) {
        return certificadoMedicoRepository.save(certificadoMedico);
    }

    public void deleteById(Long id) {
        certificadoMedicoRepository.deleteById(id);
    }

    public List<CertificadoMedico> findByPacienteId(Long pacienteId) {
        return certificadoMedicoRepository.findByPacienteId(pacienteId);
    }

    public List<CertificadoMedico> findByMedicoId(Long medicoId) {
        return certificadoMedicoRepository.findByMedicoId(medicoId);
    }
}
