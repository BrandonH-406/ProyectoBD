package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Libro;
import com.biblioteca.bibliotecabd.Model.Repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> getAllLibro() {
        return (List<Libro>) libroRepository.findAll();
    }

    public Optional<Libro> findById(int id) {
        return libroRepository.findById(id);
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void delete(Libro libro) {
        libroRepository.delete(libro);
    }

    public List<Libro> findByTitulo(String titulo){
        return libroRepository.findByTituloLike(titulo);
    }

    public List<Libro> LibrosPorCategia(String categoria){
        return libroRepository.findTitulosPorCategoria(categoria);
    }

}
