package com.example.proyecto_citas_medicas.repository;

import com.example.proyecto_citas_medicas.entities.UserTokens;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {

    @Query(value = "Select * from users_tokens Where status = 'A' and user_id = :user_id", nativeQuery = true)
    Optional<UserTokens> findItemByUserId(@Param("user_id") Long user_id);

    @Query(value = "Select * from users_tokens where status = 'A' and reset_token = :reset_token", nativeQuery = true)
    Optional<UserTokens> findItemByToken(@Param("reset_token") String reset_token);
    
    @Query(value = "Insert into users_tokens (user_id, reset_token, status) values (:user_id, :reset_token, 'A') Returning user_token_id, user_id, reset_token, login_token, status", nativeQuery = true)
    UserTokens insertItem(@Param("user_id") Long user_id, @Param("reset_token") String reset_token);

    @Query(value = "Update users_tokens set reset_token = :reset_token where status = 'A' and user_token_id = :user_token_id returning user_token_id, user_id, reset_token, login_token, status", nativeQuery = true)
    UserTokens updateItem(@Param("user_token_id") Long user_token_id, @Param("reset_token") String reset_token);
}
