package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "medication_laboratories")
public class MedicationLaboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicationLabortoryId;
    private Long medicationId;
    private Long labortairyId;
    private String grams;
    private String price;
    private String unity;
    private Character status;

    public Long getMedicationLabortoryId() {
        return this.medicationLabortoryId;
    }

    public void setMedicationLabortoryId(Long medicationLabortoryId) {
        this.medicationLabortoryId = medicationLabortoryId;
    }

    public Long getMedicationId() {
        return this.medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public Long getLabortairyId() {
        return this.labortairyId;
    }

    public void setLabortairyId(Long labortairyId) {
        this.labortairyId = labortairyId;
    }

    public Character getStatus() {
        return this.status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getGrams() {
        return this.grams;
    }

    public void setGrams(String grams) {
        this.grams = grams;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnity() {
        return this.unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }
}
