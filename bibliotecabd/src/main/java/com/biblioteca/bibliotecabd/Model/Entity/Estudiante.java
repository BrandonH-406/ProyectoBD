package com.biblioteca.bibliotecabd.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @Column(name = "id_usuario")
    private int idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "carne")
    private String carne;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(name = "anio_ingreso")
    private int anioIngreso;

    @Column(name = "semestre_actual")
    private int semestreActual;

    public Estudiante() {
    }

    public Estudiante(Usuario usuario, String carne, Carrera carrera, int anioIngreso, int semestreActual) {
        this.usuario = usuario;
        this.carne = carne;
        this.carrera = carrera;
        this.anioIngreso = anioIngreso;
        this.semestreActual = semestreActual;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public int getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(int anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public int getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(int semestreActual) {
        this.semestreActual = semestreActual;
    }

}
