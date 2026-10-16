package com.biblioteca.bibliotecabd.Model.Entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private long idBitacora;

    @Column(name = "tabla_afectada")
    private String tablaAfectada;

    @Column(name = "operacion")
    private String operacion;

    @Column(name = "id_registro")
    private String idRegistro;

    @Lob
    @Column(name = "datos_anteriores")
    private String datosAnteriores;

    @Lob
    @Column(name = "datos_nuevos")
    private String datosNuevos;

    @Column(name = "usuario_bd")
    private String usuarioBd;

    @Column(name = "fecha")
    private Timestamp fecha;

    public Bitacora() {
    }

    public Bitacora(String tablaAfectada, String operacion, String idRegistro, String datosAnteriores,
            String datosNuevos, String usuarioBd, Timestamp fecha) {
        this.tablaAfectada = tablaAfectada;
        this.operacion = operacion;
        this.idRegistro = idRegistro;
        this.datosAnteriores = datosAnteriores;
        this.datosNuevos = datosNuevos;
        this.usuarioBd = usuarioBd;
        this.fecha = fecha;
    }

    public long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getDatosAnteriores() {
        return datosAnteriores;
    }

    public void setDatosAnteriores(String datosAnteriores) {
        this.datosAnteriores = datosAnteriores;
    }

    public String getDatosNuevos() {
        return datosNuevos;
    }

    public void setDatosNuevos(String datosNuevos) {
        this.datosNuevos = datosNuevos;
    }

    public String getUsuarioBd() {
        return usuarioBd;
    }

    public void setUsuarioBd(String usuarioBd) {
        this.usuarioBd = usuarioBd;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

}
