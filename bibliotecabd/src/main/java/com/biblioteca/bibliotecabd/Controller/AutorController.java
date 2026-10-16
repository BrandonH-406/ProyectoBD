package com.biblioteca.bibliotecabd.Controller;

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

import com.biblioteca.bibliotecabd.Model.Entity.Autor;
import com.biblioteca.bibliotecabd.Service.AutorService;

@CrossOrigin
@RestController
@RequestMapping("/Autor")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping("/listaAutores")
    public List<Autor> getAll() {
        return autorService.getAllAutores();
    }

    @GetMapping("/autorId/{id}")
    public Optional<Autor> getById(@PathVariable("id") int id) {
        return autorService.findById(id);
    }

    @PostMapping("/createAutor")
    public Autor postMethodAutor(@RequestBody Autor entity) {
        if (entity != null && autorService.findById(entity.getIdAutor()).isPresent()) {
            return null;
        } else {
            return autorService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Autor updateAutor(@PathVariable("par_id") int AutorId, @RequestBody Autor updateAutor) {

        Optional<Autor> autorOptional = autorService.findById(AutorId);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            autor.setNombres(updateAutor.getNombres());
            autor.setApellidos(updateAutor.getApellidos());
            autor.setNacionalidad(updateAutor.getNacionalidad());
            return autorService.save(autor);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_autor/{par_id}")
    public Autor partialUpdateAutor(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Autor> AutorOptional = autorService.findById(par_id);

        if (AutorOptional.isPresent()) {
            Autor autor = AutorOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "nombres":
                        autor.setNombres((String) value);
                        break;
                    case "apellidos":
                        autor.setApellidos((String) value);
                        break;
                    case "nacionalidad":
                        autor.setNacionalidad((String) value);
                        break;
                }
            });

            return autorService.save(autor);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteAutor{id}")
    public void DeleteAutor(@PathVariable("id") int id) {
        Optional<Autor> AutorOptional = autorService.findById(id);
        if (AutorOptional.isPresent()) {
            autorService.delete(AutorOptional.get());
        }
    }
}
