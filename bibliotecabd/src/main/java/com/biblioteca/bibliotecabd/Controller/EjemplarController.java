package com.biblioteca.bibliotecabd.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

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
import com.biblioteca.bibliotecabd.Model.Entity.Ejemplar;
import com.biblioteca.bibliotecabd.Model.Entity.Libro;
import com.biblioteca.bibliotecabd.Model.Entity.Ubicacion;
import com.biblioteca.bibliotecabd.Service.EjemplarService;

@CrossOrigin
@RestController
@RequestMapping("/Ejemplar")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    @GetMapping("/listaEjemplares")
    public List<Ejemplar> getAll() {
        return ejemplarService.getAllEjemplares();
    }

    @GetMapping("/ejemplarId/{id}")
    public Optional<Ejemplar> getById(@PathVariable("id") int id) {
        return ejemplarService.findById(id);
    }

    @PostMapping("/createEjemplar")
    public Ejemplar postMethodEjemplar(@RequestBody Ejemplar entity) {
        if (entity != null && ejemplarService.findById(entity.getIdEjemplar()).isPresent()) {
            return null;
        } else {
            return ejemplarService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Ejemplar updateEjemplar(@PathVariable("par_id") int EjemplarId, @RequestBody Ejemplar updateEjemplar) {

        Optional<Ejemplar> ejemplarOptional = ejemplarService.findById(EjemplarId);
        if (ejemplarOptional.isPresent()) {
            Ejemplar ejemplar = ejemplarOptional.get();
            ejemplar.setFechaAdquisicion(updateEjemplar.getFechaAdquisicion());
            ejemplar.setCosto(updateEjemplar.getCosto());
            ejemplar.setCondicion(updateEjemplar.getCondicion());
            ejemplar.setEstado(updateEjemplar.getEstado());
            ejemplar.setFechaRegistro(updateEjemplar.getFechaRegistro());
            ejemplar.setLibro(updateEjemplar.getLibro());
            ejemplar.setUbicacion(updateEjemplar.getUbicacion());

            return ejemplarService.save(ejemplar);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_ejemplar/{par_id}")
    public Ejemplar partialUpdateEjemplar(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Ejemplar> EjemplarOptional = ejemplarService.findById(par_id);

        if (EjemplarOptional.isPresent()) {
            Ejemplar ejemplar = EjemplarOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "fechaAdquisicion":
                        ejemplar.setFechaAdquisicion(LocalDate.parse((String) value));
                        break;
                    case "costo":
                        ejemplar.setCosto(Double.parseDouble((String) value));
                        break;
                    case "condicion":
                        ejemplar.setCondicion((String) value);
                        break;
                    case "estado":
                        ejemplar.setEstado((String) value);
                        break;
                    case "fechaRegistro":
                        ejemplar.setFechaRegistro(LocalDate.parse((String) value));
                        break;
                    case "libro":
                        ejemplar.setLibro((Libro) value);
                        break;
                    case "ubicacion":
                        ejemplar.setUbicacion((Ubicacion) value);
                        break;
                }
            });

            return ejemplarService.save(ejemplar);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteEjemplar/{id}")
    public void DeleteEjemplar(@PathVariable("id") int id) {
        Optional<Ejemplar> EjemplarOptional = ejemplarService.findById(id);
        if (EjemplarOptional.isPresent()) {
            ejemplarService.delete(EjemplarOptional.get());
        }
    }

    @GetMapping ("/countEjemplares/{idLibro}/{estado}")
    public int CountEjemplares(@PathVariable ("idLibro") int libro, @PathVariable ("estado") String estado){
        return ejemplarService.CountEjemplares(libro, estado);
    }

}
