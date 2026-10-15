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

import com.biblioteca.bibliotecabd.Model.Entity.Ubicacion;
import com.biblioteca.bibliotecabd.Service.UbicacionService;

@CrossOrigin
@RestController
@RequestMapping("/Ubicaion")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping("/listaUbicacion")
    public List<Ubicacion> getAll() {
        return ubicacionService.getAllUbicaciones();
    }

    @GetMapping("/ubicacionId/{id}")
    public Optional<Ubicacion> getById(@PathVariable("id") int id) {
        return ubicacionService.findById(id);
    }

    @PostMapping("/createUbicaion")
    public Ubicacion postMethodUbicaion(@RequestBody Ubicacion entity) {
        if (entity != null && ubicacionService.findById(entity.getIdUbicacion()).isPresent()) {
            return null;
        } else {
            return ubicacionService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Ubicacion updateUbicacion(@PathVariable("par_id") int ubicacionId, @RequestBody Ubicacion updateubicacion) {

        Optional<Ubicacion> ubicacionOptional = ubicacionService.findById(ubicacionId);
        if (ubicacionOptional.isPresent()) {
            Ubicacion ubicacion = ubicacionOptional.get();
            ubicacion.setSala(updateubicacion.getSala());
            ubicacion.setEstanteria(updateubicacion.getEstanteria());
            ubicacion.setNivel(updateubicacion.getNivel());
            ubicacion.setDescripcion(updateubicacion.getDescripcion());
            return ubicacionService.save(ubicacion);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_ubicacion/{par_id}")
    public Ubicacion partialUpdateUbicacion(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Ubicacion> UbicacionOptional = ubicacionService.findById(par_id);

        if (UbicacionOptional.isPresent()) {
            Ubicacion ubicacion = UbicacionOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "sala":
                        ubicacion.setSala((String) value);
                        break;
                    case "estanteria":
                        ubicacion.setEstanteria((String) value);
                        break;
                    case "nivel":
                        ubicacion.setNivel(Integer.parseInt((String) value));
                        break;
                    case "descripcion":
                        ubicacion.setDescripcion((String) value);
                        break;
                }
            });

            return ubicacionService.save(ubicacion);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteUbicacion{id}")
    public void DeleteUbicacion(@PathVariable("id") int id) {
        Optional<Ubicacion> ubicacionOptional = ubicacionService.findById(id);
        if (ubicacionOptional.isPresent()) {
            ubicacionService.delete(ubicacionOptional.get());
        }
    }

}
