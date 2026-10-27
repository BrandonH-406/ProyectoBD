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

import com.biblioteca.bibliotecabd.Model.Entity.Ejemplar;
import com.biblioteca.bibliotecabd.Model.Entity.Prestamo;
import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Service.PrestamoDetalleService;

@CrossOrigin
@RestController
@RequestMapping("/PrestamoDetalle")
public class PrestamoDetalleController {

    private final PrestamoDetalleService prestamoDetalleService;

    public PrestamoDetalleController(PrestamoDetalleService prestamoDetalleService) {
        this.prestamoDetalleService = prestamoDetalleService;
    }

    @GetMapping("/listaPrestamoDetalle")
    public List<PrestamoDetalle> getAll() {
        return prestamoDetalleService.getAllCategorias();
    }

    @GetMapping("/detalleId/{id}")
    public Optional<PrestamoDetalle> getById(@PathVariable("id") int id) {
        return prestamoDetalleService.findById(id);
    }

    @PostMapping("/createDetalle")
    public PrestamoDetalle postMethodDetalle(@RequestBody PrestamoDetalle entity) {
        if (entity != null && prestamoDetalleService.findById(entity.getIdDetalle()).isPresent()) {
            return null;
        } else {
            return prestamoDetalleService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public PrestamoDetalle updateDetalle(@PathVariable("par_id") int detalleId,
            @RequestBody PrestamoDetalle updatedetalle) {

        Optional<PrestamoDetalle> detalleOptional = prestamoDetalleService.findById(detalleId);
        if (detalleOptional.isPresent()) {
            PrestamoDetalle detalle = detalleOptional.get();
            detalle.setFechaDevolucionPrevista(updatedetalle.getFechaDevolucionPrevista());
            detalle.setRenovaciones(updatedetalle.getRenovaciones());
            detalle.setEstado(updatedetalle.getEstado());
            detalle.setPrestamo(updatedetalle.getPrestamo());
            detalle.setEjemplar(updatedetalle.getEjemplar());
            return prestamoDetalleService.save(detalle);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_detalle/{par_id}")
    public PrestamoDetalle partialUpdateDetalle(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<PrestamoDetalle> DetalleOptional = prestamoDetalleService.findById(par_id);

        if (DetalleOptional.isPresent()) {
            PrestamoDetalle detalle = DetalleOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "FechaDevolucionPrevista":
                        detalle.setFechaDevolucionPrevista(LocalDate.parse((String) value));
                        break;
                    case "renovaciones":
                        detalle.setRenovaciones(Integer.parseInt((String) value));
                        break;
                    case "estado":
                        detalle.setEstado((String) value);
                        break;
                    case "prestamo":
                        detalle.setPrestamo((Prestamo) value);
                        break;
                    case "ejemplar":
                        detalle.setEjemplar((Ejemplar) value);
                }
            });

            return prestamoDetalleService.save(detalle);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteDetalle/{id}")
    public void DeleteDetalle(@PathVariable("id") int id) {
        Optional<PrestamoDetalle> detalleOptional = prestamoDetalleService.findById(id);
        if (detalleOptional.isPresent()) {
            prestamoDetalleService.delete(detalleOptional.get());
        }
    }

    @GetMapping ("/DetallesPrestamo/{id}")
    public List<PrestamoDetalle> ListaPorPrestamo(@PathVariable ("id") int idPrestamo ){
        return prestamoDetalleService.DetallesPrestamo(idPrestamo);
    }

}
