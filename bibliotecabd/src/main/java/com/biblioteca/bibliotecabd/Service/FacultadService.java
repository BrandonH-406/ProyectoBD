package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Facultad;
import com.biblioteca.bibliotecabd.Model.Repository.FacultadRepository;

@Service 
public class FacultadService {

    private final FacultadRepository facultadRepository;

    public FacultadService(FacultadRepository facultadRepository){
        this.facultadRepository = facultadRepository;
    }

    public List<Facultad> getAllFacultades(){
        return facultadRepository.findAll();
    }

    public Optional<Facultad> findById(Integer id){
        return facultadRepository.findById(id);
    }

    public Facultad save(Facultad facultad){
        return facultadRepository.save(facultad);
    }

    public void delete(Facultad facultad){
        facultadRepository.delete(facultad);
    }
}
