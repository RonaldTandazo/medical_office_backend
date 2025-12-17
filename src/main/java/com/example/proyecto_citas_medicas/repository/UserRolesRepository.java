package com.example.proyecto_citas_medicas.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.proyecto_citas_medicas.entities.UserRoles;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    @Query(value = "SELECT user_roles.user_role_id, roles.role_id, roles.name, user_roles.status FROM user_roles " +
        "JOIN roles on roles.role_id = user_roles.role_id and roles.status in (:status)" +
        "WHERE user_roles.user_id = :user_id and user_roles.status in (:status)", nativeQuery = true)
    List<Map<String, Object>> findRolesByUserId(@Param("user_id") Long user_id, @Param("status") Character[] status);
}
