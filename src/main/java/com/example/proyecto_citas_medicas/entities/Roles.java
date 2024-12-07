package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String name;
    private Character status;

    @Override
    public String toString() {
        return "Rol{" +
            "role_id=" + roleId +
            ", name='" + name + '\'' +
        '}';
    }

    // Getters y Setters
    public Long getId() {
        return roleId;
    }
    public void setId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
        this.status = status;
    }

    // Interfaz interna RoleProjection
    public interface RoleProjection {
        Long getRole_id();
        String getName();
    }
}

