package com.biblioteca.bibliotecabd.Model.Entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @OneToMany(mappedBy = "prestamo")
    private List<PrestamoDetalle> prestamoDetalle;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_bibliotecario", nullable = false)
    private PerBiblioteca bibliotecario;

    public Prestamo(){

    }
    
    public Prestamo(LocalDate fechaPrestamo, String estado, String observaciones, Usuario usuario,
            PerBiblioteca bibliotecario) {
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
        this.observaciones = observaciones;
        this.usuario = usuario;
        this.bibliotecario = bibliotecario;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<PrestamoDetalle> getPrestamoDetalle() {
        return prestamoDetalle;
    }

    public void setPrestamoDetalle(List<PrestamoDetalle> prestamoDetalle) {
        this.prestamoDetalle = prestamoDetalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PerBiblioteca getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(PerBiblioteca bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

}
