package com.biblioteca.bibliotecabd.Model.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "ejemplar"})
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private int idUbicacion;

    @Column(name = "sala")
    private String sala;

    @Column(name = "estanteria")
    private String estanteria;

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "ubicacion")
    private List<Ejemplar> ejemplar;

    public Ubicacion(){
        
    }

    public Ubicacion(String sala, String estanteria, int nivel, String descripcion) {
        this.sala = sala;
        this.estanteria = estanteria;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(String estanteria) {
        this.estanteria = estanteria;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
