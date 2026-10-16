package com.biblioteca.bibliotecabd.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bibliotecabd.Service.PrestamoService;

@CrossOrigin 
@RestController 
@RequestMapping ("/Prestamo")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService){
        this.prestamoService = prestamoService;
    }

}
