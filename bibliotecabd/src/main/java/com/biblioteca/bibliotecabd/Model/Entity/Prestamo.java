package com.biblioteca.bibliotecabd.Model.Entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "prestamoDetalle" })
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private int idPrestamo;

    @Column(name = "fecha_prestamo")
    private LocalDate fechaPrestamo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrestamoDetalle> prestamoDetalle;

    public Prestamo(LocalDate fechaPrestamo, String estado, String observaciones) {
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
        this.observaciones = observaciones;
    }

}
