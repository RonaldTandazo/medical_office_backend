package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Character status;

    @Override
    public String toString() {
        return "Medicamentos{" +
            "id=" + id +
            "nombre=" + nombre +
            ", status='" + status + '\'' +
        '}';
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
        this.status = status;
    }
}
