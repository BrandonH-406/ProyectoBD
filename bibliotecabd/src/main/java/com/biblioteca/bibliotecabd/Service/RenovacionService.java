package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Renovacion;
import com.biblioteca.bibliotecabd.Model.Repository.RenovacionRepository;

@Service 
public class RenovacionService {

    private final RenovacionRepository renovacionRepository;

    public RenovacionService(RenovacionRepository renovacionRepository){
        this.renovacionRepository = renovacionRepository;
    }
        public List<Renovacion> getAllRenovaciones(){
        return renovacionRepository.findAll();
    }

    public Optional<Renovacion> findById(int id){
        return renovacionRepository.findById(id);
    }

    public Renovacion save(Renovacion renovacion){
        return renovacionRepository.save(renovacion);
    }

    public void delete(Renovacion renovacion){
        renovacionRepository.delete(renovacion);
    }

}
