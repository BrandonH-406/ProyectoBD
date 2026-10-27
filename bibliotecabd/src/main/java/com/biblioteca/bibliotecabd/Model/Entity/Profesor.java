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
@Table(name = "profesor")
public class Profesor {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "codigo_empleado")
    private String codigoEmpleado;

    @ManyToOne
    @JoinColumn(name = "id_facultad", nullable = false)
    private Facultad facultad;

    @Column(name = "titulo_academico")
    private String tituloAcademico;

    public Profesor() {
    }

    public Profesor(Usuario usuario, String codigoEmpleado, Facultad facultad, String tituloAcademico) {
        this.usuario = usuario;
        this.codigoEmpleado = codigoEmpleado;
        this.facultad = facultad;
        this.tituloAcademico = tituloAcademico;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public String getTituloAcademico() {
        return tituloAcademico;
    }

    public void setTituloAcademico(String tituloAcademico) {
        this.tituloAcademico = tituloAcademico;
    }

}
