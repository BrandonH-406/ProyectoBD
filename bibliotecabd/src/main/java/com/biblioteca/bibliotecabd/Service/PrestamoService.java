package com.biblioteca.bibliotecabd.Service;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Repository.PrestamoRepository;

@Service 
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository){
        this.prestamoRepository = prestamoRepository;
    }

}
