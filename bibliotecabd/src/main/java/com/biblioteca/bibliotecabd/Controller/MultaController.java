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

import com.biblioteca.bibliotecabd.Model.Entity.Multa;
import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Model.Entity.Usuario;
import com.biblioteca.bibliotecabd.Service.MultaService;

@CrossOrigin
@RestController
@RequestMapping("/Multa")
public class MultaController {

    private final MultaService multaService;

    public MultaController(MultaService multaService) {
        this.multaService = multaService;
    }

    @GetMapping("/listaLibro")
    public List<Multa> getAll() {
        return multaService.getAllMulta();
    }

    @GetMapping("/multaId/{id}")
    public Optional<Multa> getById(@PathVariable("id") int id) {
        return multaService.findById(id);
    }

    @PostMapping("/createMulta")
    public Multa postMethodMulta(@RequestBody Multa entity) {
        if (entity != null && multaService.findById(entity.getIdMulta()).isPresent()) {
            return null;
        } else {
            return multaService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Multa updateMulta(@PathVariable("par_id") int multaId, @RequestBody Multa updateMulta) {

        Optional<Multa> Optional = multaService.findById(multaId);
        if (Optional.isPresent()) {            
            Multa multa = Optional.get();
            multa.setTipo(updateMulta.getTipo());
            multa.setMonto(updateMulta.getMonto());
            multa.setFechaGeneracion(updateMulta.getFechaGeneracion());
            multa.setEstado(updateMulta.getEstado());
            multa.setFechaPago(updateMulta.getFechaPago());
            multa.setObservaciones(updateMulta.getObservaciones());
            multa.setDetalle(updateMulta.getDetalle());
            multa.setCobradoPor(updateMulta.getCobradoPor());
            multa.setUsuario(updateMulta.getUsuario());            
            return multaService.save(multa);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_multa/{par_id}")
    public Multa partialUpdateLibro(@PathVariable int par_id, @RequestBody Map<String, Object> updates) {
        Optional<Multa> Optional = multaService.findById(par_id);

        if (Optional.isPresent()) {            
            Multa multa = Optional.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "tipo":
                        multa.setTipo((String) value);                        
                        break;
                    case "fechaGeneracion":
                        multa.setFechaGeneracion(LocalDate.parse((String) value));                        
                        break;
                    case "monto":
                        multa.setMonto(Double.parseDouble((String) value));                        
                        break;
                    case "estado":
                        multa.setEstado((String) value);                        
                        break;
                    case "fechaPago":
                        multa.setFechaPago(LocalDate.parse((String) value));                        
                        break;
                    case "observaciones":
                        multa.setObservaciones((String) value);                        
                        break;
                    case "cobradoPor":
                        multa.setCobradoPor((PerBiblioteca) value);                        
                        break;
                    case "usuario":
                        multa.setUsuario((Usuario) value);                        
                        break;
                    case "detalle":
                        multa.setDetalle((PrestamoDetalle) value);                        
                        break;

                }
            });

            return multaService.save(multa);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteMulta/{id}")
    public void DeleteLibro(@PathVariable("id") int id) {
        Optional<Multa> Optional = multaService.findById(id);
        if (Optional.isPresent()) {
            multaService.delete(Optional.get());
        }
    }

}
