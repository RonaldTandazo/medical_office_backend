package repository;

import entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByPacienteId(Long pacienteId);
    List<Transaccion> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
