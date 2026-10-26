package com.biblioteca.bibliotecabd.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bibliotecabd.Model.Entity.Categoria;
import com.biblioteca.bibliotecabd.Model.Entity.Editorial;
import com.biblioteca.bibliotecabd.Model.Entity.Libro;
import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Service.LibroService;

@CrossOrigin
@RestController
@RequestMapping("/Libro")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/listaLibro")
    public List<Libro> getAll() {
        return libroService.getAllLibro();
    }

    @GetMapping("/LibrosTitulo/{titulo}")
    public List<Libro> getLibroNombre(@PathVariable("titulo") String titulo) {
        return libroService.findByTitulo(titulo);
    }

    @GetMapping("/libroId/{id}")
    public Optional<Libro> getById(@PathVariable("id") Integer id) {
        return libroService.findById(id);
    }

    @PostMapping("/createLibro")
    public Libro postMethodLibro(@RequestBody Libro entity) {
        // Revisa si ya existe un libro grabado con el mismo título
        if (entity != null && entity.getTitulo() != null && libroService.portitulo(entity.getTitulo()).isPresent()) {
            // Ya existe un libro con ese título
            return null; // O lanzar una excepción / devolver un ResponseEntity.badRequest()
        } else {
            return libroService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Libro updateLibro(@PathVariable("par_id") Integer libroId, @RequestBody Libro updateLibro) {

        Optional<Libro> libroOptional = libroService.findById(libroId);
        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            libro.setTitulo(updateLibro.getTitulo());
            libro.setAnioPublicacion(updateLibro.getAnioPublicacion());
            libro.setEdicion(updateLibro.getEdicion());
            libro.setIdioma(updateLibro.getIdioma());
            libro.setNumPaginas(updateLibro.getNumPaginas());
            libro.setDescripcion(updateLibro.getDescripcion());
            libro.setEstado(updateLibro.getEstado());
            libro.setFechaRegistrado(updateLibro.getFechaRegistrado());
            libro.setEditorial(updateLibro.getEditorial());
            libro.setCategoria(updateLibro.getCategoria());
            libro.setRegistradoPor(updateLibro.getRegistradoPor());
            return libroService.save(libro);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_libro/{par_id}")
    public Libro partialUpdateLibro(@PathVariable Integer par_id, @RequestBody Map<String, Object> updates) {
        Optional<Libro> LibroOptional = libroService.findById(par_id);

        if (LibroOptional.isPresent()) {
            Libro libro = LibroOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "titulo":
                        libro.setTitulo((String) value);
                        break;
                    case "anioPublicacion":
                        libro.setAnioPublicacion(Integer.parseInt((String) value));
                        break;
                    case "edicion":
                        libro.setEdicion(Integer.parseInt((String) value));
                        break;
                    case "numPaginas":
                        libro.setNumPaginas(Integer.parseInt((String) value));
                        break;
                    case "descripcion":
                        libro.setDescripcion((String) value);
                        break;
                    case "estado":
                        libro.setEstado((char) value);
                        break;
                    case "fechaRegistro":
                        libro.setFechaRegistrado(LocalDate.parse((String) value));
                        break;
                    case "registradoPor":
                        libro.setRegistradoPor((PerBiblioteca) value);
                        break;
                    case "categoria":
                        libro.setCategoria((Categoria) value);
                        break;
                    case "editorial":
                        libro.setEditorial((Editorial) value);
                        break;

                }
            });

            return libroService.save(libro);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteLibro/{id}")
    public void DeleteLibro(@PathVariable("id") Integer id) {
        Optional<Libro> libroOptional = libroService.findById(id);
        if (libroOptional.isPresent()) {
            libroService.delete(libroOptional.get());
        }
    }

    @GetMapping("/LibrosCategoria/{categoria}")
    public List<Libro> LibrosPorCategoria(@PathVariable("categoria") String categoria) {
        return libroService.LibrosPorCategia(categoria);
    }

}
