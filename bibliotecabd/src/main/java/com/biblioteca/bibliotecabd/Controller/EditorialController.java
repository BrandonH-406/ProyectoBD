package com.biblioteca.bibliotecabd.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bibliotecabd.Model.Entity.Editorial;
import com.biblioteca.bibliotecabd.Service.EditorialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/editorial")
public class EditorialController {

    private final EditorialService editorialService;

    public EditorialController(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @GetMapping("/listaEditorial")
    public List<Editorial> getAll() {
        return editorialService.getAllEditorial();
    }

    @GetMapping("/editorialId/{id}")
    public Optional<Editorial> getById(@PathVariable("id") int id) {
        return editorialService.findById(id);
    }

    @PostMapping("/createEditorial")
    public Editorial postMethodEditorial(@RequestBody Editorial entity) {
        if (entity != null && editorialService.findById(entity.getIdEditorial()).isPresent()) {
            return null;
        } else {
            return editorialService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Editorial updateEditorial(@PathVariable("par_id") int editorialId, @RequestBody Editorial updatededitorial) {

        Optional<Editorial> editorialOptional = editorialService.findById(editorialId);
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();
            editorial.setNombre(updatededitorial.getNombre());
            editorial.setPais(updatededitorial.getPais());
            editorial.setSitioWeb(updatededitorial.getSitioWeb());
            editorial.setEstado(updatededitorial.getEstado());
            return editorialService.save(editorial);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_editorial/{par_id}")
    public Editorial partialUpdateEditoral(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Editorial> EditorialOptional = editorialService.findById(par_id);

        if (EditorialOptional.isPresent()) {
            Editorial editorial = EditorialOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "nombre":
                        editorial.setNombre((String) value);
                        break;
                    case "pais":
                        editorial.setPais((String) value);
                        break;
                    case "sitioWeb":
                        editorial.setSitioWeb((String) value);
                        break;
                    case "estado":
                        editorial.setEstado((char) value);
                        break;
                }
            });

            return editorialService.save(editorial);
        } else {
            return null;
        }
    }


    @DeleteMapping ("/deleteEditorial/{id}")
    public void DeleteEditorial(@PathVariable ("id") int id){
        Optional<Editorial> editorOptional = editorialService.findById(id);
        if(editorOptional.isPresent()){
            editorialService.delete(editorOptional.get());
        }
    }
}
