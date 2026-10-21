package com.biblioteca.bibliotecabd.Model.Entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "ejemplar" })
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private int idLibro;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @Column(name = "idioma")
    private String idioma;

    @Column(name = "edicion")
    private int edicion;

    @Column(name = "num_paginas")
    private int numPaginas;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private char estado;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistrado;

    @ManyToOne
    @JoinColumn(name = "id_editorial", nullable = false)
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "registrado_por", nullable = false)
    private PerBiblioteca registradoPor;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejemplar> ejemplar;

    public Libro(){
        
    }
    public Libro(String titulo, int anioPublicacion, int edicion, int numPaginas, String descripcion, char estado,
            LocalDate fechaRegistrado, Editorial editorial, Categoria categoria, String idioma,
            PerBiblioteca registradoPor) {
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.edicion = edicion;
        this.numPaginas = numPaginas;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaRegistrado = fechaRegistrado;
        this.editorial = editorial;
        this.categoria = categoria;
        this.idioma = idioma;
        this.registradoPor = registradoPor;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public LocalDate getFechaRegistrado() {
        return fechaRegistrado;
    }

    public void setFechaRegistrado(LocalDate fechaRegistrado) {
        this.fechaRegistrado = fechaRegistrado;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public PerBiblioteca getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(PerBiblioteca registradoPor) {
        this.registradoPor = registradoPor;
    }

    public List<Ejemplar> getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(List<Ejemplar> ejemplar) {
        this.ejemplar = ejemplar;
    }
}
