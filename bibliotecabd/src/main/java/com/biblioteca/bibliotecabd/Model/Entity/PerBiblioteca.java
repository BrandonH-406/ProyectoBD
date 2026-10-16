package com.biblioteca.bibliotecabd.Model.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_biblioteca")
public class PerBiblioteca {

    @Id
    @Column(name = "id_usuario")
    private int idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "codigo_empleado")
    private String codigoEmpleado;

    @Column(name = "puesto")
    private String puesto;

    @Column(name = "fecha_contratacion")
    private Date fechaContratacion;

    public PerBiblioteca() {
    }

    public PerBiblioteca(Usuario usuario, String codigoEmpleado, String puesto, Date fechaContratacion) {
        this.usuario = usuario;
        this.codigoEmpleado = codigoEmpleado;
        this.puesto = puesto;
        this.fechaContratacion = fechaContratacion;
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

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

}
