package com.biblioteca.bibliotecabd.Model.Entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "Libro", "renovcion", "devolucion", "multa", "prestamo" })
@Table(name = "personal_biblioteca")
public class PerBiblioteca {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

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

    @OneToMany(mappedBy = "registradoPor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> Libro;

    @OneToMany(mappedBy = "autorizadoPor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Renovacion> renovcion;

    @OneToMany(mappedBy = "recibidoPor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Devolucion> devolucion;

    @OneToMany(mappedBy = "cobradoPor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Multa> multa;

    @OneToMany(mappedBy = "bibliotecario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamo;

    public PerBiblioteca(){
        
    }

    public PerBiblioteca(Usuario usuario, String codigoEmpleado, String puesto, Date fechaContratacion) {
        this.usuario = usuario;
        this.codigoEmpleado = codigoEmpleado;
        this.puesto = puesto;
        this.fechaContratacion = fechaContratacion;
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
