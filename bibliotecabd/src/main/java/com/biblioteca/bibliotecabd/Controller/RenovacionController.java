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

import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Model.Entity.Renovacion;
import com.biblioteca.bibliotecabd.Service.RenovacionService;

@CrossOrigin
@RestController
@RequestMapping("/Renovacion")
public class RenovacionController {

    private final RenovacionService renovacionService;

    public RenovacionController(RenovacionService renovacionService) {
        this.renovacionService = renovacionService;
    }

    @GetMapping("/listaRenovaciones")
    public List<Renovacion> getAll() {
        return renovacionService.getAllRenovaciones();
    }

    @GetMapping("/RenovacionId/{id}")
    public Optional<Renovacion> getById(@PathVariable("id") int id) {
        return renovacionService.findById(id);
    }

    @PostMapping("/createRenovacion")
    public Renovacion postMethodRenovacion(@RequestBody Renovacion entity) {
        if (entity != null && renovacionService.findById(entity.getIdRenovacion()).isPresent()) {
            return null;
        } else {
            return renovacionService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Renovacion updateRenovacion(@PathVariable("par_id") int RenovacionId,
            @RequestBody Renovacion updateRenovacion) {

        Optional<Renovacion> renovacionOptional = renovacionService.findById(RenovacionId);
        if (renovacionOptional.isPresent()) {
            Renovacion renovacion = renovacionOptional.get();
            renovacion.setFechaRenovacion(updateRenovacion.getFechaRenovacion());
            renovacion.setFechaPrevistaAnt(updateRenovacion.getFechaPrevistaAnt());
            renovacion.setFechaPrevistaNueva(updateRenovacion.getFechaPrevistaNueva());
            renovacion.setAutorizadoPor(updateRenovacion.getAutorizadoPor());
            renovacion.setDetalle(updateRenovacion.getDetalle());
            return renovacionService.save(renovacion);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_Renovacion/{par_id}")
    public Renovacion partialUpdateRenovacion(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Renovacion> RenovacionOptional = renovacionService.findById(par_id);

        if (RenovacionOptional.isPresent()) {
            Renovacion renovacion = RenovacionOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "fechaRenovacion":
                        renovacion.setFechaRenovacion(LocalDate.parse((String) value));
                        break;
                    case "fechaPrevistaAnt":
                        renovacion.setFechaPrevistaAnt(LocalDate.parse((String) value));
                        break;
                    case "fechaPrevistaNueva":
                        renovacion.setFechaPrevistaNueva(LocalDate.parse((String) value));
                        break;
                    case "autorizadoPor":
                        renovacion.setAutorizadoPor((PerBiblioteca) value);
                        break;
                    case "detalle":
                        renovacion.setDetalle((PrestamoDetalle) value);
                }
            });

            return renovacionService.save(renovacion);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteRenovacion/{id}")
    public void DeleteRenovacion(@PathVariable("id") int id) {
        Optional<Renovacion> RenovacionOptional = renovacionService.findById(id);
        if (RenovacionOptional.isPresent()) {
            renovacionService.delete(RenovacionOptional.get());
        }
    }

}
