package com.example.proyecto_citas_medicas.entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.*;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationId;
    private String name;
    private String type;
    @Type(JsonStringType.class)
    @Column(columnDefinition = "json")
    private List<String> diseases;
    private Character status;

    public Long getMedicationId() {
        return this.medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    public Character getStatus() {
        return this.status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
}
