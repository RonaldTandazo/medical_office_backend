package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Role;
import com.example.proyecto_citas_medicas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE email = :email and status = 'A'", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT roles.role_id, roles.name FROM user_roles " +
            "JOIN roles on roles.role_id = user_roles.role_id and roles.status = 'A'" +
            "WHERE user_roles.user_id = :user_id and user_roles.status = 'A'", nativeQuery = true)
    List<Role.RoleProjection> findByRolesByUserId(@Param("user_id") BigInteger user_id);
}
