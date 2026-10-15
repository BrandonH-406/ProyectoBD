package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Ubicacion;
import com.biblioteca.bibliotecabd.Model.Repository.UbicacionRepository;

@Service 
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository){
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ubicacion> getAllUbicaciones(){
        return ubicacionRepository.findAll();
    }

    public Optional<Ubicacion> findById(int id){
        return ubicacionRepository.findById(id);
    }

    public Ubicacion save(Ubicacion ubicacion){
        return ubicacionRepository.save(ubicacion);
    }

    public void delete(Ubicacion ubicacion){
        ubicacionRepository.delete(ubicacion);
    }

}
