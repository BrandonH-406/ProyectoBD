package com.biblioteca.bibliotecabd.Controller;

import java.sql.Timestamp;
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

import com.biblioteca.bibliotecabd.Model.Entity.Bitacora;
import com.biblioteca.bibliotecabd.Service.BitacoraService;

@CrossOrigin
@RestController
@RequestMapping("/Bitacora")
public class BitacoraController {

    private final BitacoraService bitacoraService;

    public BitacoraController(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @GetMapping("/listaBitacoras")
    public List<Bitacora> getAll() {
        return bitacoraService.getAllBitacoras();
    }

    @GetMapping("/bitacoraId/{id}")
    public Optional<Bitacora> getById(@PathVariable("id") long id) {
        return bitacoraService.findById(id);
    }

    @PostMapping("/createBitacora")
    public Bitacora postMethodBitacora(@RequestBody Bitacora entity) {
        if (entity != null && bitacoraService.findById(entity.getIdBitacora()).isPresent()) {
            return null;
        } else {
            return bitacoraService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public Bitacora updateBitacora(@PathVariable("par_id") long BitacoraId, @RequestBody Bitacora updateBitacora) {

        Optional<Bitacora> bitacoraOptional = bitacoraService.findById(BitacoraId);
        if (bitacoraOptional.isPresent()) {
            Bitacora bitacora = bitacoraOptional.get();
            bitacora.setTablaAfectada(updateBitacora.getTablaAfectada());
            bitacora.setOperacion(updateBitacora.getOperacion());
            bitacora.setIdRegistro(updateBitacora.getIdRegistro());
            bitacora.setDatosAnteriores(updateBitacora.getDatosAnteriores());
            bitacora.setDatosNuevos(updateBitacora.getDatosNuevos());
            bitacora.setUsuarioBd(updateBitacora.getUsuarioBd());
            bitacora.setFecha(updateBitacora.getFecha());
            return bitacoraService.save(bitacora);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_bitacora/{par_id}")
    public Bitacora partialUpdateBitacora(@PathVariable long par_id, @RequestBody Map<String, Object> updates) {
        Optional<Bitacora> BitacoraOptional = bitacoraService.findById(par_id);

        if (BitacoraOptional.isPresent()) {
            Bitacora bitacora = BitacoraOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "tablaAfectada":
                        bitacora.setTablaAfectada((String) value);
                        break;
                    case "operacion":
                        bitacora.setOperacion((String) value);
                        break;
                    case "idRegistro":
                        bitacora.setIdRegistro((String) value);
                        break;
                    case "datosAnteriores":
                        bitacora.setDatosAnteriores((String) value);
                        break;
                    case "datosNuevos":
                        bitacora.setDatosNuevos((String) value);
                        break;
                    case "usuarioBd":
                        bitacora.setUsuarioBd((String) value);
                        break;
                    case "fecha":
                        bitacora.setFecha(Timestamp.valueOf((String) value));
                        break;
                }
            });

            return bitacoraService.save(bitacora);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteBitacora/{id}")
    public void DeleteBitacora(@PathVariable("id") long id) {
        Optional<Bitacora> BitacoraOptional = bitacoraService.findById(id);
        if (BitacoraOptional.isPresent()) {
            bitacoraService.delete(BitacoraOptional.get());
        }
    }
}
