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
    private int idRol;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "max_prestamos")
    private int maxPrestamos;

    @Column(name = "dias_prestamo")
    private int diasPrestamo;

    @Column(name = "max_renovaciones")
    private int maxRenovaciones;

    @Column(name = "multa_por_dia")
    private double multaPorDia;

    @Column(name = "estado")
    private char estado;

    public Rol() {
    }

    public Rol(String nombre, String descripcion, int maxPrestamos, int diasPrestamo, int maxRenovaciones,
            double multaPorDia, char estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.maxPrestamos = maxPrestamos;
        this.diasPrestamo = diasPrestamo;
        this.maxRenovaciones = maxRenovaciones;
        this.multaPorDia = multaPorDia;
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
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

    public int getMaxPrestamos() {
        return maxPrestamos;
    }

    public void setMaxPrestamos(int maxPrestamos) {
        this.maxPrestamos = maxPrestamos;
    }

    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    public void setDiasPrestamo(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public int getMaxRenovaciones() {
        return maxRenovaciones;
    }

    public void setMaxRenovaciones(int maxRenovaciones) {
        this.maxRenovaciones = maxRenovaciones;
    }

    public double getMultaPorDia() {
        return multaPorDia;
    }

    public void setMultaPorDia(double multaPorDia) {
        this.multaPorDia = multaPorDia;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

}
