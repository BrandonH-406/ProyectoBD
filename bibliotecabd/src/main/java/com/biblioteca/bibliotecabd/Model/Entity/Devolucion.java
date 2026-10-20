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
@Table(name = "devolucion")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devolucion")
    private int idDevolucion;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "condicion_ejemplar")
    private String condicionEjemplar;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "dias_atraso")
    private int diasAtraso;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    private PrestamoDetalle detalle;

    @ManyToOne
    @JoinColumn(name = "recibido_por", nullable = false)
    private PerBiblioteca recibidoPor;

    public Devolucion(LocalDate fechaDevolucion, String condicionEjemplar, String observaciones, int diasAtraso,
            PrestamoDetalle detalle, PerBiblioteca recibidoPor) {
        this.fechaDevolucion = fechaDevolucion;
        this.condicionEjemplar = condicionEjemplar;
        this.observaciones = observaciones;
        this.diasAtraso = diasAtraso;
        this.detalle = detalle;
        this.recibidoPor = recibidoPor;
    }

    public int getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getCondicionEjemplar() {
        return condicionEjemplar;
    }

    public void setCondicionEjemplar(String condicionEjemplar) {
        this.condicionEjemplar = condicionEjemplar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public PrestamoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PrestamoDetalle detalle) {
        this.detalle = detalle;
    }

    public PerBiblioteca getRecibidoPor() {
        return recibidoPor;
    }

    public void setRecibidoPor(PerBiblioteca recibidoPor) {
        this.recibidoPor = recibidoPor;
    }
}
