package com.biblioteca.bibliotecabd.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "max_prestamos")
    private Integer maxPrestamos;

    @Column(name = "dias_prestamo")
    private Integer diasPrestamo;

    @Column(name = "max_renovaciones")
    private Integer maxRenovaciones;

    @Column(name = "multa_por_dia")
    private Double multaPorDia;

    @Column(name = "estado")
    private Character estado;

    public Rol() {
    }

    public Rol(String nombre, String descripcion, Integer maxPrestamos, Integer diasPrestamo, Integer maxRenovaciones,
            Double multaPorDia, Character estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.maxPrestamos = maxPrestamos;
        this.diasPrestamo = diasPrestamo;
        this.maxRenovaciones = maxRenovaciones;
        this.multaPorDia = multaPorDia;
        this.estado = estado;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMaxPrestamos() {
        return maxPrestamos;
    }

    public void setMaxPrestamos(Integer maxPrestamos) {
        this.maxPrestamos = maxPrestamos;
    }

    public Integer getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(Integer diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public Integer getMaxRenovaciones() {
        return maxRenovaciones;
    }

    public void setMaxRenovaciones(Integer maxRenovaciones) {
        this.maxRenovaciones = maxRenovaciones;
    }

    public Double getMultaPorDia() {
        return multaPorDia;
    }

    public void setMultaPorDia(Double multaPorDia) {
        this.multaPorDia = multaPorDia;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

}
