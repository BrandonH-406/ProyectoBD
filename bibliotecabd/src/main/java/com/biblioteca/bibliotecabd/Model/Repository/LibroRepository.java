package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.bibliotecabd.Model.Entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    public List<Libro> findByTituloLike(String titulo);

    @Query("SELECT l FROM Libro l JOIN l.categoria c WHERE c.nombre = :nombreCategoria")
    List<Libro> findTitulosPorCategoria(@Param("nombreCategoria") String nombreCategoria);
}
