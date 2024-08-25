package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger role_id;
    private String name;

    @Override
    public String toString() {
        return "Rol{" +
            "role_id=" + role_id +
            ", name='" + name + '\'' +
        '}';
    }

    // Getters y Setters
    public BigInteger getId() {
        return role_id;
    }
    public void setId(BigInteger role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Interfaz interna RoleProjection
    public interface RoleProjection {
        BigInteger getRole_id();
        String getName();
    }
}

