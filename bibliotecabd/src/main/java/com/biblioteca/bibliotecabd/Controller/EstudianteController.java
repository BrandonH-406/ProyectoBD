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

import com.biblioteca.bibliotecabd.Model.Entity.Estudiante;
import com.biblioteca.bibliotecabd.Service.CarreraService;
import com.biblioteca.bibliotecabd.Service.EstudianteService;

@CrossOrigin
@RestController
@RequestMapping("/Estudiante")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final CarreraService carreraService;

    public EstudianteController(EstudianteService estudianteService, CarreraService carreraService) {
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
    }

    @GetMapping("/listaEstudiantes")
    public List<Estudiante> getAll() {
        return estudianteService.getAllEstudiantes();
    }

    @GetMapping("/estudianteId/{id}")
    public Optional<Estudiante> getById(@PathVariable("id") Integer id) {
        return estudianteService.findById(id);
    }

    @PostMapping("/createEstudiante")
    public Estudiante postMethodEstudiante(@RequestBody Estudiante entity) {
        if (entity != null && entity.getUsuario() != null
                && estudianteService.findById(entity.getUsuario().getIdUsuario()).isPresent()) {
            return null;
        } else {
            return estudianteService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Estudiante updateEstudiante(@PathVariable("par_id") Integer EstudianteId,
            @RequestBody Estudiante updateEstudiante) {

        Optional<Estudiante> estudianteOptional = estudianteService.findById(EstudianteId);
        if (estudianteOptional.isPresent()) {
            Estudiante estudiante = estudianteOptional.get();
            estudiante.setCarne(updateEstudiante.getCarne());
            estudiante.setCarrera(updateEstudiante.getCarrera());
            estudiante.setAnioIngreso(updateEstudiante.getAnioIngreso());
            estudiante.setSemestreActual(updateEstudiante.getSemestreActual());
            return estudianteService.save(estudiante);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_estudiante/{par_id}")
    public Estudiante partialUpdateEstudiante(@PathVariable Integer par_id, @RequestBody Map<String, Object> updates) {
        Optional<Estudiante> EstudianteOptional = estudianteService.findById(par_id);

        if (EstudianteOptional.isPresent()) {
            Estudiante estudiante = EstudianteOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "carne":
                        estudiante.setCarne((String) value);
                        break;
                    case "idCarrera":
                        carreraService.findById(((Number) value).intValue()).ifPresent(estudiante::setCarrera);
                        break;
                    case "anioIngreso":
                        estudiante.setAnioIngreso(((Number) value).intValue());
                        break;
                    case "semestreActual":
                        estudiante.setSemestreActual(((Number) value).intValue());
                        break;
                }
            });

            return estudianteService.save(estudiante);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteEstudiante/{id}")
    public void DeleteEstudiante(@PathVariable("id") Integer id) {
        Optional<Estudiante> EstudianteOptional = estudianteService.findById(id);
        if (EstudianteOptional.isPresent()) {
            estudianteService.delete(EstudianteOptional.get());
        }
    }

    @GetMapping("/listaEstudianteUsuaurio")
    public List<Estudiante> listarEmpleados() {
        return estudianteService.obtenerTodosLosEstudiantesUsuario();
    }
}
