package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users_tokens")
public class UserTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_token_id;
    private Long user_id;
    private String reset_token;
    private String login_token;
    private Character status;

    public Long getUserTokenId() {
        return user_token_id;
    }
    public void setUserTokenId(Long user_token_id) {
        this.user_token_id = user_token_id;
    }

    public Long getUserId() {
        return user_id;
    }
    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getResetToken() {
        return reset_token;
    }
    public void setResetToken(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getLoginToken() {
        return login_token;
    }
    public void setLoginToken(String login_token) {
        this.login_token = login_token;
    }

    public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
        this.status = status;
    }
}
