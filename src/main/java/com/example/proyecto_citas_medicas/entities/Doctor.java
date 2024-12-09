package com.example.proyecto_citas_medicas.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    private String name;
    private String email;
    private String phone;
    private Long userId;
    private Character status;
    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<DoctorPatient> doctorPatients;

    public List<DoctorPatient> getDoctorPatients() {
        return this.doctorPatients;
    }

    public void setDoctorPatients(List<DoctorPatient> doctorPatients) {
        this.doctorPatients = doctorPatients;
    }
    
    public Long getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Character getStatus() {
        return this.status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }
}
