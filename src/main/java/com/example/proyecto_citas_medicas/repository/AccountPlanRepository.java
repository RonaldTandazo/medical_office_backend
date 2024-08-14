package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.AccountPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPlanRepository extends JpaRepository<AccountPlan, Long> {
}
