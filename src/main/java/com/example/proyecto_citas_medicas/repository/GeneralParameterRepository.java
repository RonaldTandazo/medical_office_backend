package repository;

import entities.GeneralParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralParameterRepository extends JpaRepository<GeneralParameter, Long> {
}