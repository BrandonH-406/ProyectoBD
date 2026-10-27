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

import com.biblioteca.bibliotecabd.Model.Entity.Facultad;
import com.biblioteca.bibliotecabd.Service.FacultadService;

@CrossOrigin
@RestController
@RequestMapping("/Facultad")
public class FacultadController {

    private final FacultadService facultadService;

    public FacultadController(FacultadService facultadService) {
        this.facultadService = facultadService;
    }

    @GetMapping("/listaFacultades")
    public List<Facultad> getAll() {
        return facultadService.getAllFacultades();
    }

    @GetMapping("/facultadId/{id}")
    public Optional<Facultad> getById(@PathVariable("id") Integer id) {
        return facultadService.findById(id);
    }

    @PostMapping("/createFacultad")
    public Facultad postMethodFacultad(@RequestBody Facultad entity) {
        if (entity != null && facultadService.findById(entity.getIdFacultad()).isPresent()) {
            return null;
        } else {
            return facultadService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Facultad updateFacultad(@PathVariable("par_id") Integer FacultadId, @RequestBody Facultad updateFacultad) {

        Optional<Facultad> facultadOptional = facultadService.findById(FacultadId);
        if (facultadOptional.isPresent()) {
            Facultad facultad = facultadOptional.get();
            facultad.setCodigo(updateFacultad.getCodigo());
            facultad.setNombre(updateFacultad.getNombre());
            facultad.setEstado(updateFacultad.getEstado());
            return facultadService.save(facultad);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_facultad/{par_id}")
    public Facultad partialUpdateFacultad(@PathVariable Integer par_id, @RequestBody Map<String, Object> updates) {
        Optional<Facultad> FacultadOptional = facultadService.findById(par_id);

        if (FacultadOptional.isPresent()) {
            Facultad facultad = FacultadOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "codigo":
                        facultad.setCodigo((String) value);
                        break;
                    case "nombre":
                        facultad.setNombre((String) value);
                        break;
                    case "estado":
                        facultad.setEstado(((String) value).charAt(0));
                        break;
                }
            });

            return facultadService.save(facultad);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteFacultad/{id}")
    public void DeleteFacultad(@PathVariable("id") Integer id) {
        Optional<Facultad> FacultadOptional = facultadService.findById(id);
        if (FacultadOptional.isPresent()) {
            facultadService.delete(FacultadOptional.get());
        }
    }
}
