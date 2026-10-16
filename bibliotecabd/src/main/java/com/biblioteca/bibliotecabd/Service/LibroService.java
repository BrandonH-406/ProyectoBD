package com.biblioteca.bibliotecabd.Service;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Repository.LibroRepository;

@Service 
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository){
        this.libroRepository = libroRepository;
    }

}
