package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "specialities")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specialityId;
    private String description;

    // Getters y Setters
    public Long getId() {
        return specialityId;
    }

    public void setId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
