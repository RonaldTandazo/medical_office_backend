package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "users_tokens")
public class UserTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger user_token_id;
    private BigInteger user_id;
    private String reset_token;
    private String login_token;


    public String toString() {
        return "UserTokens{" +
            "user_tokens_id=" + user_token_id +
            ", user_id='" + user_id + '\'' +
            ", reset_token='" + reset_token + '\'' +
            ", login_token='" + login_token + '\'' +
        '}';
    }

    public BigInteger getUser_token_id() {
        return user_token_id;
    }

    public void setUser_tokens_id(BigInteger user_token_id) {
        this.user_token_id = user_token_id;
    }

    public BigInteger getUser_id() {
        return user_id;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    public interface UsersTokensProjection {
        BigInteger getUser_token_id();
        BigInteger getUser_id();
        String getReset_token();
        String getLogin_token();
    }
}
