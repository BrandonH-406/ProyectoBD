package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Autor;
import com.biblioteca.bibliotecabd.Model.Repository.AutorRepository;

@Service 
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

        public List<Autor> getAllAutores(){
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(int id){
        return autorRepository.findById(id);
    }

    public Autor save(Autor autor){
        return autorRepository.save(autor);
    }

    public void delete(Autor autor){
        autorRepository.delete(autor);
    }
}
