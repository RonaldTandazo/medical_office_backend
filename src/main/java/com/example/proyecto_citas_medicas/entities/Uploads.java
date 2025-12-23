package com.example.proyecto_citas_medicas.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "uploads")
public class Uploads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ruc;
    private String proveedor;
    private LocalDateTime fecha;
    private Character status;

    @OneToMany(
        mappedBy = "upload",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<UploadDetail> details;

    @Override
    public String toString() {
        return "Uploads{" +
            "id=" + id +
            "fecha=" + fecha +
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

    public String getRuc() {
        return ruc;
    }
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getProveedor() {
        return proveedor;
    }
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<UploadDetail> getDetails() {
        return details;
    }

    public void setDetails(List<UploadDetail> details) {
        this.details = details;
    }


    public Character getStatus() {
        return status;
    }
    public void setStatus(Character status) {
        this.status = status;
    }
}
