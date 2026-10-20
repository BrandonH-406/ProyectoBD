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

import com.biblioteca.bibliotecabd.Model.Entity.Devolucion;
import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Service.DevolucionService;

@CrossOrigin
@RestController
@RequestMapping("/Devoluciones")
public class DevolucionController {

    private final DevolucionService devolucionService;

    public DevolucionController(DevolucionService devolucionService) {
        this.devolucionService = devolucionService;
    }

    @GetMapping("/listaDevoluciones")
    public List<Devolucion> getAll() {
        return devolucionService.getAllDevoluciones();
    }

    @GetMapping("/devolucionId/{id}")
    public Optional<Devolucion> getById(@PathVariable("id") int id) {
        return devolucionService.findById(id);
    }

    @PostMapping("/createDevolucion")
    public Devolucion postMethodDevolucion(@RequestBody Devolucion entity) {
        if (entity != null && devolucionService.findById(entity.getIdDevolucion()).isPresent()) {
            return null;
        } else {
            return devolucionService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Devolucion updateDevolucion(@PathVariable("par_id") int devolucionId,
            @RequestBody Devolucion updateDevolucion) {

        Optional<Devolucion> devolucionOptional = devolucionService.findById(devolucionId);
        if (devolucionOptional.isPresent()) {
            Devolucion devolucion = devolucionOptional.get();

            devolucion.setFechaDevolucion(updateDevolucion.getFechaDevolucion());
            devolucion.setCondicionEjemplar(updateDevolucion.getCondicionEjemplar());
            devolucion.setDiasAtraso(updateDevolucion.getDiasAtraso());
            devolucion.setObservaciones(updateDevolucion.getObservaciones());
            devolucion.setDetalle(updateDevolucion.getDetalle());
            devolucion.setRecibidoPor(updateDevolucion.getRecibidoPor());
            return devolucionService.save(devolucion);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_devolucion/{par_id}")
    public Devolucion partialUpdateDevolucion(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Devolucion> devolucionOptional = devolucionService.findById(par_id);

        if (devolucionOptional.isPresent()) {

            Devolucion devolucion = devolucionOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "fechaDevolucion":
                        devolucion.setFechaDevolucion(LocalDate.parse((String) value));
                        break;
                    case "condicionEjemplar":
                        devolucion.setCondicionEjemplar((String) value);
                        break;
                    case "diasAtraso":
                        devolucion.setDiasAtraso(Integer.parseInt((String) value));
                        break;
                    case "observaciones":
                        devolucion.setObservaciones((String) value);
                        break;
                    case "detalle":
                        devolucion.setDetalle((PrestamoDetalle) value);
                        break;
                    case "recibidoPor":
                        devolucion.setRecibidoPor((PerBiblioteca) value);
                }
            });

            return devolucionService.save(devolucion);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteDevolucion/{id}")
    public void DeleteDevolucion(@PathVariable("id") int id) {
        Optional<Devolucion> Optional = devolucionService.findById(id);
        if (Optional.isPresent()) {
            devolucionService.delete(Optional.get());
        }
    }

}
