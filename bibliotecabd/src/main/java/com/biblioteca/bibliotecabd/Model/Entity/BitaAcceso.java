package com.biblioteca.bibliotecabd.Model.Entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bitacora_acceso")
public class BitaAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acceso")
    private long idAcceso;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "username_intentado")
    private String usernameIntentado;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "direccion_ip")
    private String direccionIp;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "fecha")
    private Timestamp fecha;

    public BitaAcceso() {
    }

    public BitaAcceso(Usuario usuario, String usernameIntentado, String resultado, String direccionIp,
            String detalle, Timestamp fecha) {
        this.usuario = usuario;
        this.usernameIntentado = usernameIntentado;
        this.resultado = resultado;
        this.direccionIp = direccionIp;
        this.detalle = detalle;
        this.fecha = fecha;
    }

    public long getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(long idAcceso) {
        this.idAcceso = idAcceso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsernameIntentado() {
        return usernameIntentado;
    }

    public void setUsernameIntentado(String usernameIntentado) {
        this.usernameIntentado = usernameIntentado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

}
