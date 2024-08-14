package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PermissionRepository.java
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
