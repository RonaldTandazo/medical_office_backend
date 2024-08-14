package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.CertificadoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CertificadoMedicoRepository extends JpaRepository<CertificadoMedico, Long> {
    List<CertificadoMedico> findByPacienteId(Long pacienteId);
    List<CertificadoMedico> findByMedicoId(Long medicoId);
}
