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

import com.biblioteca.bibliotecabd.Model.Entity.Profesor;
import com.biblioteca.bibliotecabd.Service.FacultadService;
import com.biblioteca.bibliotecabd.Service.ProfesorService;

@CrossOrigin
@RestController
@RequestMapping("/Profesor")
public class ProfesorController {

    private final ProfesorService profesorService;
    private final FacultadService facultadService;

    public ProfesorController(ProfesorService profesorService, FacultadService facultadService) {
        this.profesorService = profesorService;
        this.facultadService = facultadService;
    }

    @GetMapping("/listaProfesores")
    public List<Profesor> getAll() {
        return profesorService.getAllProfesores();
    }

    @GetMapping("/profesorId/{id}")
    public Optional<Profesor> getById(@PathVariable("id") Integer id) {
        return profesorService.findById(id);
    }

    @PostMapping("/createProfesor")
    public Profesor postMethodProfesor(@RequestBody Profesor entity) {
        if (entity != null && entity.getUsuario() != null
                && profesorService.findById(entity.getUsuario().getIdUsuario()).isPresent()) {
            return null;
        } else {
            return profesorService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Profesor updateProfesor(@PathVariable("par_id") Integer ProfesorId, @RequestBody Profesor updateProfesor) {

        Optional<Profesor> profesorOptional = profesorService.findById(ProfesorId);
        if (profesorOptional.isPresent()) {
            Profesor profesor = profesorOptional.get();
            profesor.setCodigoEmpleado(updateProfesor.getCodigoEmpleado());
            profesor.setFacultad(updateProfesor.getFacultad());
            profesor.setTituloAcademico(updateProfesor.getTituloAcademico());
            return profesorService.save(profesor);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_profesor/{par_id}")
    public Profesor partialUpdateProfesor(@PathVariable Integer par_id, @RequestBody Map<String, Object> updates) {
        Optional<Profesor> ProfesorOptional = profesorService.findById(par_id);

        if (ProfesorOptional.isPresent()) {
            Profesor profesor = ProfesorOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "codigoEmpleado":
                        profesor.setCodigoEmpleado((String) value);
                        break;
                    case "idFacultad":
                        facultadService.findById(((Number) value).intValue()).ifPresent(profesor::setFacultad);
                        break;
                    case "tituloAcademico":
                        profesor.setTituloAcademico((String) value);
                        break;
                }
            });

            return profesorService.save(profesor);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteProfesor/{id}")
    public void DeleteProfesor(@PathVariable("id") Integer id) {
        Optional<Profesor> ProfesorOptional = profesorService.findById(id);
        if (ProfesorOptional.isPresent()) {
            profesorService.delete(ProfesorOptional.get());
        }
    }

    @GetMapping("/listaProfesorUsuaurio")
    public List<Profesor> listarEmpleados() {
        return profesorService.obtenerTodosLosProfesoresUsuario();
    }
}
