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
@Table(name = "multa")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multa")
    private int idMulta;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "monto")
    private double monto;

    @Column(name = "fecha_generacion")
    private LocalDate fechaGeneracion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "cobrado_por", nullable = false)
    private PerBiblioteca cobradoPor;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    private PrestamoDetalle detalle;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Multa(String tipo, double monto, LocalDate fechaGeneracion, String estado, LocalDate fechaPago,
            String observaciones, PerBiblioteca cobradoPor, PrestamoDetalle detalle, Usuario usuario) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaGeneracion = fechaGeneracion;
        this.estado = estado;
        this.fechaPago = fechaPago;
        this.observaciones = observaciones;
        this.cobradoPor = cobradoPor;
        this.detalle = detalle;
        this.usuario = usuario;
    }

    public int getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(int idMulta) {
        this.idMulta = idMulta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public PerBiblioteca getCobradoPor() {
        return cobradoPor;
    }

    public void setCobradoPor(PerBiblioteca cobradoPor) {
        this.cobradoPor = cobradoPor;
    }

    public PrestamoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PrestamoDetalle detalle) {
        this.detalle = detalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
