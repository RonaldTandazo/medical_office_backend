package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
}