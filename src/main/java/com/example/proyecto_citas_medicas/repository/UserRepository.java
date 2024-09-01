package com.example.proyecto_citas_medicas.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.proyecto_citas_medicas.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT roles.role_id, roles.name FROM user_roles " +
            "JOIN roles on roles.role_id = user_roles.role_id and roles.status = 'A'" +
            "WHERE user_roles.user_id = :user_id and user_roles.status = 'A'", nativeQuery = true)
    List<Map<String, Object>> findRolesByUserId(@Param("user_id") Long user_id);

    @Query(value = "Update users set password = :new_password where user_id = :user_id and status = 'A' Returning user_id, username, email, password, status", nativeQuery = true)
    User updatePassword(@Param("user_id") Long user_id, @Param("new_password") String new_password);

    @Query(value = "UPDATE users SET username = :username, identification = :identification, gender = :gender, "
        + "age = :age, phonenumber = :phonenumber "
        + "WHERE user_id = :userId AND status = 'A' "
        + "RETURNING user_id, username, email, identification, gender, age, phonenumber, avatar, password, status",
        nativeQuery = true)
    User updateItem(
        @Param("userId") Long userId,
        @Param("username") String username,
        @Param("identification") String identification,
        @Param("gender") Character gender,
        @Param("age") Long age,
        @Param("phonenumber") String phonenumber
    );

}
