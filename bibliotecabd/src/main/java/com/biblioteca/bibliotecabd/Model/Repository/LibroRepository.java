package com.biblioteca.bibliotecabd.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.bibliotecabd.Model.Entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    public List<Libro> findByTituloLike(String titulo);

}
