package com.biblioteca.bibliotecabd.Controller;

import java.sql.Date;
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
import com.biblioteca.bibliotecabd.Service.PerBibliotecaService;

@CrossOrigin
@RestController
@RequestMapping("/PerBiblioteca")
public class PerBibliotecaController {

    private final PerBibliotecaService perBibliotecaService;

    public PerBibliotecaController(PerBibliotecaService perBibliotecaService) {
        this.perBibliotecaService = perBibliotecaService;
    }

    @GetMapping("/listaPerBiblioteca")
    public List<PerBiblioteca> getAll() {
        return perBibliotecaService.getAllPerBiblioteca();
    }

    @GetMapping("/perBibliotecaId/{id}")
    public Optional<PerBiblioteca> getById(@PathVariable("id") int id) {
        return perBibliotecaService.findById(id);
    }

    @PostMapping("/createPerBiblioteca")
    public PerBiblioteca postMethodPerBiblioteca(@RequestBody PerBiblioteca entity) {
        if (entity != null && entity.getUsuario() != null
                && perBibliotecaService.findById(entity.getUsuario().getIdUsuario()).isPresent()) {
            return null;
        } else {
            return perBibliotecaService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public PerBiblioteca updatePerBiblioteca(@PathVariable("par_id") int PerBibliotecaId,
            @RequestBody PerBiblioteca updatePerBiblioteca) {

        Optional<PerBiblioteca> perBibliotecaOptional = perBibliotecaService.findById(PerBibliotecaId);
        if (perBibliotecaOptional.isPresent()) {
            PerBiblioteca perBiblioteca = perBibliotecaOptional.get();
            perBiblioteca.setCodigoEmpleado(updatePerBiblioteca.getCodigoEmpleado());
            perBiblioteca.setPuesto(updatePerBiblioteca.getPuesto());
            perBiblioteca.setFechaContratacion(updatePerBiblioteca.getFechaContratacion());
            return perBibliotecaService.save(perBiblioteca);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_perBiblioteca/{par_id}")
    public PerBiblioteca partialUpdatePerBiblioteca(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<PerBiblioteca> PerBibliotecaOptional = perBibliotecaService.findById(par_id);

        if (PerBibliotecaOptional.isPresent()) {
            PerBiblioteca perBiblioteca = PerBibliotecaOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "codigoEmpleado":
                        perBiblioteca.setCodigoEmpleado((String) value);
                        break;
                    case "puesto":
                        perBiblioteca.setPuesto((String) value);
                        break;
                    case "fechaContratacion":
                        perBiblioteca.setFechaContratacion(Date.valueOf((String) value));
                        break;
                }
            });

            return perBibliotecaService.save(perBiblioteca);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deletePerBiblioteca/{id}")
    public void DeletePerBiblioteca(@PathVariable("id") int id) {
        Optional<PerBiblioteca> PerBibliotecaOptional = perBibliotecaService.findById(id);
        if (PerBibliotecaOptional.isPresent()) {
            perBibliotecaService.delete(PerBibliotecaOptional.get());
        }
    }
}
