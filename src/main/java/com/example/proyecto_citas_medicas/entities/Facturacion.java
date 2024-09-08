package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "facturaciones")
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreFacturado;
    private String direccionFacturacion;
    private String telefonoFacturacion;
    private String cedulaFacturacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Patient paciente;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFacturado() {
        return nombreFacturado;
    }

    public void setNombreFacturado(String nombreFacturado) {
        this.nombreFacturado = nombreFacturado;
    }

    public String getDireccionFacturacion() {
        return direccionFacturacion;
    }

    public void setDireccionFacturacion(String direccionFacturacion) {
        this.direccionFacturacion = direccionFacturacion;
    }

    public String getTelefonoFacturacion() {
        return telefonoFacturacion;
    }

    public void setTelefonoFacturacion(String telefonoFacturacion) {
        this.telefonoFacturacion = telefonoFacturacion;
    }

    public String getCedulaFacturacion() {
        return cedulaFacturacion;
    }

    public void setCedulaFacturacion(String cedulaFacturacion) {
        this.cedulaFacturacion = cedulaFacturacion;
    }

    public Patient getPaciente() {
        return paciente;
    }

    public void setPaciente(Patient paciente) {
        this.paciente = paciente;
    }
}
