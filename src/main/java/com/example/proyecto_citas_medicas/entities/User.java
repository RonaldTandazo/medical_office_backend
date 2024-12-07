package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String identification;
    private Character gender;
    private Long age;
    private String phonenumber;
    private String avatar;
    private String password;
    private String status;

    @Override
    public String toString() {
        return "User{" +
            "user_id=" + userId +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", identification='" + identification + '\'' +
            ", gender='" + gender + '\'' +
            ", age='" + age + '\'' +
            ", phonenumber='" + phonenumber + '\'' +
            ", avatar='" + avatar + '\'' +
            ", password='" + password + '\'' +
            ", status='" + status + '\'' +
        '}';
    }

    // Getters y Setters
    public Long getId() {
        return userId;
    }
    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentification() {
        return identification;
    }
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Character getGender() {
        return gender;
    }
    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
