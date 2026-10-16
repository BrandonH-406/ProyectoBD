package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.PerBiblioteca;
import com.biblioteca.bibliotecabd.Model.Repository.PerBibliotecaRepository;

@Service 
public class PerBibliotecaService {

    private final PerBibliotecaRepository perBibliotecaRepository;

    public PerBibliotecaService(PerBibliotecaRepository perBibliotecaRepository){
        this.perBibliotecaRepository = perBibliotecaRepository;
    }

    public List<PerBiblioteca> getAllPerBiblioteca(){
        return perBibliotecaRepository.findAll();
    }

    public Optional<PerBiblioteca> findById(int id){
        return perBibliotecaRepository.findById(id);
    }

    public PerBiblioteca save(PerBiblioteca perBiblioteca){
        return perBibliotecaRepository.save(perBiblioteca);
    }

    public void delete(PerBiblioteca perBiblioteca){
        perBibliotecaRepository.delete(perBiblioteca);
    }
}
