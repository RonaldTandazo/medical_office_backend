package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.Role;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.entities.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE email = :email and status = 'A'", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT roles.role_id, roles.name FROM user_roles " +
            "JOIN roles on roles.role_id = user_roles.role_id and roles.status = 'A'" +
            "WHERE user_roles.user_id = :user_id and user_roles.status = 'A'", nativeQuery = true)
    List<Role.RoleProjection> findByRolesByUserId(@Param("user_id") Long user_id);

    @Query(value = "Select * from users_tokens Where status = 'A' and user_id = :user_id", nativeQuery = true)
    Optional<UserTokens.UsersTokensProjection> findUserTokenByUserId(@Param("user_id") Long user_id);

    @Query(value = "Insert into users_tokens (user_id, reset_token, status) values (:user_id, :reset_token, 'A') Returning user_token_id, user_id, reset_token, login_token, status", nativeQuery = true)
    Optional<UserTokens.UsersTokensProjection> insertItem(@Param("user_id") Long user_id, @Param("reset_token") String reset_token);

    @Query(value = "Update users_tokens set reset_token = :reset_token where status = 'A' and user_token_id = :user_token_id returning user_token_id, user_id, reset_token, login_token, status", nativeQuery = true)
    Optional<UserTokens.UsersTokensProjection> updateUserResetToken(@Param("user_token_id") Long user_token_id, @Param("reset_token") String reset_token);

    @Query(value = "Select users_tokens.user_token_id, users_tokens.user_id from users_tokens where status = 'A' and reset_token = :reset_token", nativeQuery = true)
    Optional<UserTokens.UsersTokensProjection> findUserByToken(@Param("reset_token") String reset_token);

    @Query(value = "Update users set password = :new_password where user_id = :user_id and status = 'A' Returning user_id, username, email, password, status", nativeQuery = true)
    Optional<User> updatePassword(@Param("user_id") Long user_id, @Param("new_password") String new_password);
}
