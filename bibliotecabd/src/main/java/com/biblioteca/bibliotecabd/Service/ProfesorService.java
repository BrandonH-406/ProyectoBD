package com.biblioteca.bibliotecabd.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotecabd.Model.Entity.Profesor;
import com.biblioteca.bibliotecabd.Model.Repository.ProfesorRepository;

@Service
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    public Optional<Profesor> findById(Integer id) {
        return profesorRepository.findById(id);
    }

    public Profesor save(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public void delete(Profesor profesor) {
        profesorRepository.delete(profesor);
    }

    public List<Profesor> obtenerTodosLosProfesoresUsuario() {
        return profesorRepository.findAllConUsuario();
    }
}
