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

import com.biblioteca.bibliotecabd.Model.Entity.Rol;
import com.biblioteca.bibliotecabd.Service.RolService;

@CrossOrigin
@RestController
@RequestMapping("/Rol")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/listaRoles")
    public List<Rol> getAll() {
        return rolService.getAllRoles();
    }

    @GetMapping("/rolId/{id}")
    public Optional<Rol> getById(@PathVariable("id") int id) {
        return rolService.findById(id);
    }

    @PostMapping("/createRol")
    public Rol postMethodRol(@RequestBody Rol entity) {
        if (entity != null && rolService.findById(entity.getIdRol()).isPresent()) {
            return null;
        } else {
            return rolService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Rol updateRol(@PathVariable("par_id") int RolId, @RequestBody Rol updateRol) {

        Optional<Rol> rolOptional = rolService.findById(RolId);
        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();
            rol.setNombre(updateRol.getNombre());
            rol.setDescripcion(updateRol.getDescripcion());
            rol.setMaxPrestamos(updateRol.getMaxPrestamos());
            rol.setDiasPrestamo(updateRol.getDiasPrestamo());
            rol.setMaxRenovaciones(updateRol.getMaxRenovaciones());
            rol.setMultaPorDia(updateRol.getMultaPorDia());
            rol.setEstado(updateRol.getEstado());
            return rolService.save(rol);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_rol/{par_id}")
    public Rol partialUpdateRol(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Rol> RolOptional = rolService.findById(par_id);

        if (RolOptional.isPresent()) {
            Rol rol = RolOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "nombre":
                        rol.setNombre((String) value);
                        break;
                    case "descripcion":
                        rol.setDescripcion((String) value);
                        break;
                    case "maxPrestamos":
                        rol.setMaxPrestamos(((Number) value).intValue());
                        break;
                    case "diasPrestamo":
                        rol.setDiasPrestamo(((Number) value).intValue());
                        break;
                    case "maxRenovaciones":
                        rol.setMaxRenovaciones(((Number) value).intValue());
                        break;
                    case "multaPorDia":
                        rol.setMultaPorDia(((Number) value).doubleValue());
                        break;
                    case "estado":
                        rol.setEstado(((String) value).charAt(0));
                        break;
                }
            });

            return rolService.save(rol);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteRol/{id}")
    public void DeleteRol(@PathVariable("id") int id) {
        Optional<Rol> RolOptional = rolService.findById(id);
        if (RolOptional.isPresent()) {
            rolService.delete(RolOptional.get());
        }
    }
}
