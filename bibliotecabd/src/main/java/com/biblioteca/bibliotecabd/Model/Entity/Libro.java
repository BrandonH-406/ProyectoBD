package com.biblioteca.bibliotecabd.Model.Entity;

import java.sql.Date;
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
    private Date fechaRegistrado;

    @ManyToOne
    @JoinColumn(name = "id_editorial", nullable = false)
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ejemplar> ejemplar;

    public Libro(String titulo, int anioPublicacion, int edicion, int numPaginas, String descripcion, char estado,
            Date fechaRegistrado, Editorial editorial, Categoria categoria, String idioma) {
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
    }
}
