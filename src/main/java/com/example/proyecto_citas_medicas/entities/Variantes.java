package com.example.proyecto_citas_medicas.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "variantes")
public class Variantes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long medicamentoId;
    private String descripcion;
    private String presentacion;
    private Integer cantidad;
    private String lote;
    private String elaboracion;
    private String caducidad;
    private String sanitario;
    private Double precio;
    private Double total;
    private Character status;

    @Override
    public String toString() {
        return "Variantes{" +
            "id=" + id +
            "medicamentoId=" + medicamentoId +
            "descripcion=" + descripcion +
            "presentacion=" + presentacion +
            "cantidad=" + cantidad +
            "lote=" + lote +
            "elaboracion=" + elaboracion +
            "caducidad=" + caducidad +
            "sanitario=" + sanitario +
            "precio=" + precio +
            "total=" + total +
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

    public Long getMedicamentoId() {
        return medicamentoId;
    }
    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getLote() {
        return lote;
    }
    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getElaboracion() {
        return elaboracion;
    }
    public void setElaboracion(String elaboracion) {
        this.elaboracion = elaboracion;
    }

    public String getCaducidad() {
        return caducidad;
    }
    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public String getSanitario() {
        return sanitario;
    }
    public void setSanitario(String sanitario) {
        this.sanitario = sanitario;
    }

    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }

    public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
        this.status = status;
    }
}
