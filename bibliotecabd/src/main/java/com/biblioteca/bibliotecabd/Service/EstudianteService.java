package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Estudiante;
import com.biblioteca.bibliotecabd.Model.Repository.EstudianteRepository;

@Service 
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> getAllEstudiantes(){
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> findById(int id){
        return estudianteRepository.findById(id);
    }

    public Estudiante save(Estudiante estudiante){
        return estudianteRepository.save(estudiante);
    }

    public void delete(Estudiante estudiante){
        estudianteRepository.delete(estudiante);
    }
}
