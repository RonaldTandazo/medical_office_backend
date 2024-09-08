package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pacientes")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String identification;
    private String direction;
    private String phone;
    private Double weight;
    private Double height;
    private int doctor_id;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Facturacion> facturaciones;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Facturacion> getFacturaciones() {
        return facturaciones;
    }

    public void setFacturaciones(Set<Facturacion> facturaciones) {
        this.facturaciones = facturaciones;
    }

    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double weight){
        this.weight = weight;
    }

    public Double getHeight(){
        return height;
    }

    public void setHeight(Double height){
        this.height = height;
    }

    public int getDocto_Id(){
        return doctor_id;
    }

    public void setDocto_Id(int doctor_id){
        this.doctor_id = doctor_id;
    }
}
