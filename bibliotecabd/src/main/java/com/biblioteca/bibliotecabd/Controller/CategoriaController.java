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

import com.biblioteca.bibliotecabd.Model.Entity.Categoria;
import com.biblioteca.bibliotecabd.Service.CategoriaService;

@CrossOrigin
@RestController
@RequestMapping("/Categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listaCategoria")
    public List<Categoria> getAll() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/categoriaId/{id}")
    public Optional<Categoria> getById(@PathVariable("id") int id) {
        return categoriaService.findById(id);
    }

    @PostMapping("/createCategoria")
    public Categoria postMethodCategoria(@RequestBody Categoria entity) {
        if (entity != null && categoriaService.findById(entity.getIdCategoria()).isPresent()) {
            return null;
        } else {
            return categoriaService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Categoria updateCategoria(@PathVariable("par_id") int categoriaId, @RequestBody Categoria updatecategoria) {

        Optional<Categoria> categoriaOptional = categoriaService.findById(categoriaId);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            categoria.setNombre(updatecategoria.getNombre());
            categoria.setDescripcion(updatecategoria.getDescripcion());
            return categoriaService.save(categoria);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_categoria/{par_id}")
    public Categoria partialUpdateCategoria(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Categoria> CategoriaOptional = categoriaService.findById(par_id);

        if (CategoriaOptional.isPresent()) {
            Categoria categoria = CategoriaOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "nombre":
                        categoria.setNombre((String) value);
                        break;
                    case "descripcion":
                        categoria.setDescripcion((String) value);
                }
            });

            return categoriaService.save(categoria);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteEditorial{id}")
    public void DeleteCategoria(@PathVariable("id") int id) {
        Optional<Categoria> categoriaOptional = categoriaService.findById(id);
        if (categoriaOptional.isPresent()) {
            categoriaService.delete(categoriaOptional.get());
        }
    }
}
