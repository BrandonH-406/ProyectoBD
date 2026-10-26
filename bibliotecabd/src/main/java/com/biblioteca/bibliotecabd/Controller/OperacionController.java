package com.biblioteca.bibliotecabd.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bibliotecabd.Model.Dto.DevolucionRequest;
import com.biblioteca.bibliotecabd.Model.Dto.PrestamoRequest;
import com.biblioteca.bibliotecabd.Model.Entity.PrestamoDetalle;
import com.biblioteca.bibliotecabd.Service.OperacionService;

@CrossOrigin
@RestController
@RequestMapping("/Operacion")
public class OperacionController {

    private final OperacionService operacionService;

    public OperacionController(OperacionService operacionService) {
        this.operacionService = operacionService;
    }

    @GetMapping("/usuario/{codigo}")
    public Map<String, Object> resumenUsuario(@PathVariable("codigo") String codigo) {
        return operacionService.resumenUsuario(codigo);
    }

    @GetMapping("/detallesUsuario/{codigo}")
    public List<PrestamoDetalle> detallesUsuario(@PathVariable("codigo") String codigo) {
        return operacionService.detallesUsuario(codigo);
    }

    @PostMapping("/prestar")
    public List<PrestamoDetalle> prestar(@RequestBody PrestamoRequest datos) {
        return operacionService.prestar(datos);
    }

    @PostMapping("/renovar/{idDetalle}/{idBibliotecario}")
    public PrestamoDetalle renovar(@PathVariable("idDetalle") int idDetalle,
            @PathVariable("idBibliotecario") int idBibliotecario) {
        return operacionService.renovar(idDetalle, idBibliotecario);
    }

    @PostMapping("/devolver/{idDetalle}")
    public Map<String, Object> devolver(@PathVariable("idDetalle") int idDetalle, @RequestBody DevolucionRequest datos) {
        return operacionService.devolver(idDetalle, datos);
    }

    // Las reglas de negocio que no se cumplen regresan 400 con el mensaje para mostrarlo en Angular
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> reglaNoCumplida(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
    }
}
