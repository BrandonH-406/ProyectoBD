package com.biblioteca.bibliotecabd.Controller;

import com.biblioteca.bibliotecabd.Controller.EstudianteController;
import com.biblioteca.bibliotecabd.Model.Repository.EstudianteRepository;
import com.biblioteca.bibliotecabd.Service.EstudianteService;
import com.biblioteca.bibliotecabd.Service.RenovacionService;
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

import com.biblioteca.bibliotecabd.Model.Entity.Estudiante;
import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Entity.Prestamo;
import com.biblioteca.bibliotecabd.Model.Entity.Usuario;
import com.biblioteca.bibliotecabd.Service.PrestamoService;

@CrossOrigin
@RestController
@RequestMapping("/Prestamo")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;      
    }

    @GetMapping("/listaPrestamo")
    public List<Prestamo> getAll() {
        return prestamoService.getAllPrestamos();
    }

    @GetMapping("/PrestamoId/{id}")
    public Optional<Prestamo> getById(@PathVariable("id") int id) {
        return prestamoService.findById(id);
    }

    @PostMapping("/createPrestamo")
    public Prestamo postMethodPrestamo(@RequestBody Prestamo entity) {
        if (entity != null && prestamoService.findById(entity.getIdPrestamo()).isPresent()) {
            return null;
        } else {
            return prestamoService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Prestamo updatePrestamo(@PathVariable("par_id") int Id,
            @RequestBody Prestamo updatePrestamo) {

        Optional<Prestamo> Optional = prestamoService.findById(Id);
        if (Optional.isPresent()) {
            Prestamo prestamo = Optional.get();
            prestamo.setFechaPrestamo(updatePrestamo.getFechaPrestamo());
            prestamo.setEstado(updatePrestamo.getEstado());
            prestamo.setObservaciones(updatePrestamo.getObservaciones());
            prestamo.setBibliotecario(updatePrestamo.getBibliotecario());
            prestamo.setUsuario(updatePrestamo.getUsuario());
            return prestamoService.save(prestamo);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_Prestamo/{par_id}")
    public Prestamo partialUpdatePrestamo(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Prestamo> Optional = prestamoService.findById(par_id);

        if (Optional.isPresent()) {        
            Prestamo prestamo = Optional.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "fechaPrestamo":
                        prestamo.setFechaPrestamo(LocalDate.parse((String) value));                        
                        break;
                    case "estado":
                        prestamo.setEstado((String) value);                        
                        break;
                    case "observaciones":
                        prestamo.setObservaciones((String) value);                        
                        break;
                    case "bibliotecario":
                        prestamo.setBibliotecario((PerBiblioteca) value);                        
                        break;
                    case "usuario":
                        prestamo.setUsuario((Usuario) value);                        
                }
            });

            return prestamoService.save(prestamo);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deletePrestamo/{id}")
    public void Delete(@PathVariable("id") int id) {
        Optional<Prestamo> Optional = prestamoService.findById(id);
        if (Optional.isPresent()) {
            prestamoService.delete(Optional.get());
        }
    }

    @GetMapping ("/PrestamosUsuari/{codigo}")
        public List<Prestamo> PrestamosPorUsuario(@PathVariable ("codigo") String codigo){
            return prestamoService.PrestamosUsuarios(codigo);        
    }

}
