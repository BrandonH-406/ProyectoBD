package com.biblioteca.bibliotecabd.Model.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "renovacion")
public class Renovacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_renovacion")
    private int idRenovacion;

    @Column(name = "fecha_renovacion")
    private LocalDate fechaRenovacion;

    @Column(name = "fecha_prevista_ant")
    private LocalDate fechaPrevistaAnt;

    @Column(name = "fecha_prevista_nueva")
    private LocalDate fechaPrevistaNueva;

    @ManyToOne
    @JoinColumn(name = "autorizado_por", nullable = false)
    private PerBiblioteca autorizadoPor;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    private PrestamoDetalle detalle;

    public Renovacion(){

    }
    
    public Renovacion(LocalDate fechaRenovacion, LocalDate fechaPrevistaAnt, LocalDate fechaPrevistaNueva,
            PerBiblioteca autorizadoPor, PrestamoDetalle detalle) {
        this.fechaRenovacion = fechaRenovacion;
        this.fechaPrevistaAnt = fechaPrevistaAnt;
        this.fechaPrevistaNueva = fechaPrevistaNueva;
        this.autorizadoPor = autorizadoPor;
        this.detalle = detalle;
    }

    public int getIdRenovacion() {
        return idRenovacion;
    }

    public void setIdRenovacion(int idRenovacion) {
        this.idRenovacion = idRenovacion;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public LocalDate getFechaPrevistaAnt() {
        return fechaPrevistaAnt;
    }

    public void setFechaPrevistaAnt(LocalDate fechaPrevistaAnt) {
        this.fechaPrevistaAnt = fechaPrevistaAnt;
    }

    public LocalDate getFechaPrevistaNueva() {
        return fechaPrevistaNueva;
    }

    public void setFechaPrevistaNueva(LocalDate fechaPrevistaNueva) {
        this.fechaPrevistaNueva = fechaPrevistaNueva;
    }

    public PerBiblioteca getAutorizadoPor() {
        return autorizadoPor;
    }

    public void setAutorizadoPor(PerBiblioteca autorizadoPor) {
        this.autorizadoPor = autorizadoPor;
    }

    public PrestamoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PrestamoDetalle detalle) {
        this.detalle = detalle;
    }

}
