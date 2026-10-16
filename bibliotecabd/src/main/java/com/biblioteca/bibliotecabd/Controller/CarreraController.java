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

import com.biblioteca.bibliotecabd.Model.Entity.Carrera;
import com.biblioteca.bibliotecabd.Service.CarreraService;
import com.biblioteca.bibliotecabd.Service.FacultadService;

@CrossOrigin
@RestController
@RequestMapping("/Carrera")
public class CarreraController {

    private final CarreraService carreraService;
    private final FacultadService facultadService;

    public CarreraController(CarreraService carreraService, FacultadService facultadService) {
        this.carreraService = carreraService;
        this.facultadService = facultadService;
    }

    @GetMapping("/listaCarreras")
    public List<Carrera> getAll() {
        return carreraService.getAllCarreras();
    }

    @GetMapping("/carreraId/{id}")
    public Optional<Carrera> getById(@PathVariable("id") int id) {
        return carreraService.findById(id);
    }

    @PostMapping("/createCarrera")
    public Carrera postMethodCarrera(@RequestBody Carrera entity) {
        if (entity != null && carreraService.findById(entity.getIdCarrera()).isPresent()) {
            return null;
        } else {
            return carreraService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Carrera updateCarrera(@PathVariable("par_id") int CarreraId, @RequestBody Carrera updateCarrera) {

        Optional<Carrera> carreraOptional = carreraService.findById(CarreraId);
        if (carreraOptional.isPresent()) {
            Carrera carrera = carreraOptional.get();
            carrera.setFacultad(updateCarrera.getFacultad());
            carrera.setCodigo(updateCarrera.getCodigo());
            carrera.setNombre(updateCarrera.getNombre());
            carrera.setEstado(updateCarrera.getEstado());
            return carreraService.save(carrera);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_carrera/{par_id}")
    public Carrera partialUpdateCarrera(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Carrera> CarreraOptional = carreraService.findById(par_id);

        if (CarreraOptional.isPresent()) {
            Carrera carrera = CarreraOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "idFacultad":
                        facultadService.findById(((Number) value).intValue()).ifPresent(carrera::setFacultad);
                        break;
                    case "codigo":
                        carrera.setCodigo((String) value);
                        break;
                    case "nombre":
                        carrera.setNombre((String) value);
                        break;
                    case "estado":
                        carrera.setEstado(((String) value).charAt(0));
                        break;
                }
            });

            return carreraService.save(carrera);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteCarrera/{id}")
    public void DeleteCarrera(@PathVariable("id") int id) {
        Optional<Carrera> CarreraOptional = carreraService.findById(id);
        if (CarreraOptional.isPresent()) {
            carreraService.delete(CarreraOptional.get());
        }
    }
}
