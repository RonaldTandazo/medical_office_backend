package repository;

import entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PermissionRepository.java
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
