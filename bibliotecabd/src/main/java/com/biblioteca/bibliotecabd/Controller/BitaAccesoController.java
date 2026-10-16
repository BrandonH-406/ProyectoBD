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

import com.biblioteca.bibliotecabd.Model.Entity.BitaAcceso;
import com.biblioteca.bibliotecabd.Service.BitaAccesoService;
import com.biblioteca.bibliotecabd.Service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/BitaAcceso")
public class BitaAccesoController {

    private final BitaAccesoService bitaAccesoService;
    private final UsuarioService usuarioService;

    public BitaAccesoController(BitaAccesoService bitaAccesoService, UsuarioService usuarioService) {
        this.bitaAccesoService = bitaAccesoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listaBitaAccesos")
    public List<BitaAcceso> getAll() {
        return bitaAccesoService.getAllBitaAccesos();
    }

    @GetMapping("/bitaAccesoId/{id}")
    public Optional<BitaAcceso> getById(@PathVariable("id") long id) {
        return bitaAccesoService.findById(id);
    }

    @PostMapping("/createBitaAcceso")
    public BitaAcceso postMethodBitaAcceso(@RequestBody BitaAcceso entity) {
        if (entity != null && bitaAccesoService.findById(entity.getIdAcceso()).isPresent()) {
            return null;
        } else {
            return bitaAccesoService.save(entity);
        }
    }

    @PutMapping("/update/{par_id}")
    public BitaAcceso updateBitaAcceso(@PathVariable("par_id") long BitaAccesoId,
            @RequestBody BitaAcceso updateBitaAcceso) {

        Optional<BitaAcceso> bitaAccesoOptional = bitaAccesoService.findById(BitaAccesoId);
        if (bitaAccesoOptional.isPresent()) {
            BitaAcceso bitaAcceso = bitaAccesoOptional.get();
            bitaAcceso.setUsuario(updateBitaAcceso.getUsuario());
            bitaAcceso.setUsernameIntentado(updateBitaAcceso.getUsernameIntentado());
            bitaAcceso.setResultado(updateBitaAcceso.getResultado());
            bitaAcceso.setDireccionIp(updateBitaAcceso.getDireccionIp());
            bitaAcceso.setDetalle(updateBitaAcceso.getDetalle());
            bitaAcceso.setFecha(updateBitaAcceso.getFecha());
            return bitaAccesoService.save(bitaAcceso);
        } else {
            return null;
        }
    }

    @PatchMapping("/update_partial_bitaAcceso/{par_id}")
    public BitaAcceso partialUpdateBitaAcceso(@PathVariable long par_id, @RequestBody Map<String, Object> updates) {
        Optional<BitaAcceso> BitaAccesoOptional = bitaAccesoService.findById(par_id);

        if (BitaAccesoOptional.isPresent()) {
            BitaAcceso bitaAcceso = BitaAccesoOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "idUsuario":
                        usuarioService.findById(((Number) value).intValue()).ifPresent(bitaAcceso::setUsuario);
                        break;
                    case "usernameIntentado":
                        bitaAcceso.setUsernameIntentado((String) value);
                        break;
                    case "resultado":
                        bitaAcceso.setResultado((String) value);
                        break;
                    case "direccionIp":
                        bitaAcceso.setDireccionIp((String) value);
                        break;
                    case "detalle":
                        bitaAcceso.setDetalle((String) value);
                        break;
                    case "fecha":
                        bitaAcceso.setFecha(Timestamp.valueOf((String) value));
                        break;
                }
            });

            return bitaAccesoService.save(bitaAcceso);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteBitaAcceso/{id}")
    public void DeleteBitaAcceso(@PathVariable("id") long id) {
        Optional<BitaAcceso> BitaAccesoOptional = bitaAccesoService.findById(id);
        if (BitaAccesoOptional.isPresent()) {
            bitaAccesoService.delete(BitaAccesoOptional.get());
        }
    }
}
